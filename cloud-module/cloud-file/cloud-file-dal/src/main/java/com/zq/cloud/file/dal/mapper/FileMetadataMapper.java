package com.zq.cloud.file.dal.mapper;

import com.zq.cloud.file.dal.model.FileMetadata;
import tk.mybatis.mapper.common.Mapper;

public interface FileMetadataMapper extends Mapper<FileMetadata> {


     FileMetadata findByFileId(Long fileId);
}