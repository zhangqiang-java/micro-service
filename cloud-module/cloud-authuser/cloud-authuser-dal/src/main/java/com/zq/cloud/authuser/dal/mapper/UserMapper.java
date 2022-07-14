package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.dto.UserSearchDto;
import com.zq.cloud.authuser.dal.model.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Objects;

public interface UserMapper extends Mapper<User> {

    default User findByMobile(String mobile) {
        User user = new User();
        user.setMobile(mobile);
        return this.selectOne(user);
    }

    default List<User> findPage(UserSearchDto searchDto) {
        WeekendSqls<User> sqls = WeekendSqls.custom();
        if (Objects.nonNull(searchDto.getNickname())) {
            sqls.andLike(User::getNickname, "%" + searchDto.getNickname() + "%");
        }

        if (Objects.nonNull(searchDto.getRealName())) {
            sqls.andLike(User::getRealName, "%" + searchDto.getRealName() + "%");
        }

        if (Objects.nonNull(searchDto.getAddress())) {
            sqls.andLike(User::getAddress, "%" + searchDto.getAddress() + "%");
        }

        if (Objects.nonNull(searchDto.getMobile())) {
            sqls.andEqualTo(User::getMobile, searchDto.getMobile());
        }

        return this.selectByExample(new Example.Builder(User.class).where(sqls).orderByDesc("id").build());

    }
}