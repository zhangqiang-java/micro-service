package com.zq.cloud.file.core.service.impl;

import com.zq.cloud.file.core.dto.FileMetaData;
import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.service.FileService;
import com.zq.cloud.file.dal.mapper.FileMapper;
import com.zq.cloud.file.dal.mapper.FileMetadataMapper;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * 文件服务
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private StorageProvider provider;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileMetadataMapper metadataMapper;


    /**
     * 上传文件
     *
     * @param uploadRequestDto
     * @return
     */
    @Transactional
    @Override
    public FileResultVo upload(FileUploadRequestDto uploadRequestDto) {
        //1.文件上传
        try {
            MultipartFile file = uploadRequestDto.getFile();
            BusinessAssertUtils.isFalse(file.isEmpty(), "请选择要上传的文件");
            FileMetaData fileMetaData = createFileMetaData(uploadRequestDto);
            provider.upload(file.getInputStream(), fileMetaData);
        } catch (IOException e) {
            BusinessAssertUtils.fail("上传文件异常");
        }

        //2.入库持久化


        //3.生成预览url
        return null;
    }


    /**
     * 生成文件元数据信息
     *
     * @return
     */
    private FileMetaData createFileMetaData(FileUploadRequestDto uploadRequestDto) {
        MultipartFile file = uploadRequestDto.getFile();
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        FileMetaData metaData = new FileMetaData();
        metaData.setOriginalFileName(originalFilename);
        metaData.setFileSize(file.getSize());
        metaData.setFileType(FilenameUtils.getExtension(originalFilename));
        return metaData;
    }
}
