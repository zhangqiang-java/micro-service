package com.zq.cloud.starter.mybatis.mapper;


import com.zq.cloud.dto.exception.BusinessException;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Optional;
import java.util.Set;

public class UpdateListProvider extends MapperTemplate {

    public UpdateListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateListByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        //tableName
        String tableName = SqlHelper.getDynamicTableName(entityClass, this.tableName(entityClass), "example");

        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '" + ms.getId() + " 方法参数为空')\"/>");
        sql.append("UPDATE ");
        sql.append(tableName);
        sql.append(" <set>");
        //实体类所有字段
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);

        //实体类对应id字段
        EntityColumn idColumn = columnList
                .stream()
                .filter(EntityColumn::isId).findFirst()
                .orElseThrow(() -> new BusinessException("实体类id不存在"));

        // 组装所有set sql
        columnList.forEach(tempColumn -> {
            if (!tempColumn.isId() && tempColumn.isUpdatable() && !tempColumn.getEntityField().isAnnotationPresent(Version.class)) {
                appendSetInfo(sql, tempColumn, idColumn, tableName);
            }
        });

        // version字段，仅支持可自增类型的字段
        Optional<EntityColumn> versionColumn = columnList
                .stream()
                .filter(tempColumn -> tempColumn.getEntityField().isAnnotationPresent(Version.class)).findFirst();
        if (versionColumn.isPresent()) {
            EntityColumn entityColumn = versionColumn.get();
            sql.append(entityColumn.getColumn() + " = " + entityColumn.getColumn() + " + " + 1);
        }
        sql.append("</set>");

        // where
        appendWhereInfo(sql, idColumn, versionColumn);
        return sql.toString();
    }

    private void appendSetInfo(StringBuilder sql, EntityColumn column, EntityColumn idColumn, String tableName) {
        sql.append("<trim prefix=\"`" + column.getColumn() + "` = CASE " + idColumn.getColumn() + "\" suffix=\"END , \">");
        sql.append("<foreach collection=\"list\" item=\"item\">");
        sql.append(" WHEN #{item." + idColumn.getEntityField().getName() + "} THEN #{item." + column.getEntityField().getName() + "} ");
        sql.append("</foreach>");
        sql.append("</trim>");
    }

    private void appendWhereInfo(StringBuilder sql, EntityColumn idColumn, Optional<EntityColumn> versionColumnOptional) {
        sql.append(" WHERE ");
        if (versionColumnOptional.isPresent()) {
            EntityColumn versionColumn = versionColumnOptional.get();
            sql.append("(" + idColumn.getColumn() + "," + versionColumn.getColumn() + ")" + " IN");
            sql.append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">");
            sql.append(" (");
            sql.append(" #{item." + idColumn.getEntityField().getName() + "},#{item." + versionColumn.getEntityField().getName() + "} ");
        } else {
            sql.append("(" + idColumn.getColumn() + ")" + " IN");
            sql.append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">");
            sql.append(" (");
            sql.append(" #{item." + idColumn.getEntityField().getName() + "}");
        }
        sql.append(" )");
        sql.append("</foreach>");
    }
}
