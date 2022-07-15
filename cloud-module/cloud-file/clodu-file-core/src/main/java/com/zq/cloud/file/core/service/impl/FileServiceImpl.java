package com.zq.cloud.file.core.service.impl;

import com.zq.cloud.file.core.config.FileProperties;
import com.zq.cloud.file.core.constant.RedisKeyConstant;
import com.zq.cloud.file.core.dto.FileResultVo;
import com.zq.cloud.file.core.dto.FileUploadRequestDto;
import com.zq.cloud.file.core.dto.FileViewDto;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.service.FileService;
import com.zq.cloud.file.dal.mapper.FileMapper;
import com.zq.cloud.file.dal.mapper.FileMetadataMapper;
import com.zq.cloud.file.dal.model.File;
import com.zq.cloud.file.dal.model.FileMetadata;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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
        FileMetadata fileMetaData = this.createFileMetaData(uploadRequestDto);
        MultipartFile multipartFile = uploadRequestDto.getFile();
        BusinessAssertUtils.isFalse(multipartFile.isEmpty(), "请选择要上传的文件");
        try {
            provider.upload(multipartFile.getInputStream(), fileMetaData);
        } catch (IOException e) {
            BusinessAssertUtils.fail("上传文件异常");
        }

        //2.入库持久化
        metadataMapper.insert(fileMetaData);
        File file = new File();
        file.setId(SnowFlakeUtils.getNextId());
        file.setSystemCode(uploadRequestDto.getSystemCode());
        file.setBizCode(uploadRequestDto.getBizCode());
        file.setMetadataId(fileMetaData.getId());
        fileMapper.insert(file);

        //3.生成预览url
        String previewUrl = this.cratePreviewUrl(file.getId(), fileMetaData.getFileExt(), uploadRequestDto.getValidityEnable());

        //4.构建响应
        FileResultVo resultVo = new FileResultVo();
        resultVo.setId(file.getId());
        resultVo.setFileName(StringUtils.isNotBlank(uploadRequestDto.getCustomFileName())
                ? uploadRequestDto.getCustomFileName() : multipartFile.getName());
        resultVo.setPreviewUrl(previewUrl);
        return resultVo;
    }

    /**
     * 查看文件
     *
     * @param fileId
     * @param authorityId
     * @return
     */
    @Override
    public FileViewDto view(Long fileId, String authorityId, HttpServletRequest request) {
        //有时效性的预览
        if (StringUtils.isNotBlank(authorityId)) {
            String fileIdStr = stringRedisTemplate.opsForValue().get(RedisKeyConstant.getPreviewUrlAuthKey(authorityId));
            BusinessAssertUtils.notBlank(fileIdStr, "预览链接已失效");
            fileId = Long.parseLong(fileIdStr);
        }


        FileMetadata byFileId = metadataMapper.findByFileId(fileId);
        BusinessAssertUtils.notNull(byFileId, "文件不存在");


        FileViewDto viewDto = new FileViewDto();
        viewDto.setFileName(byFileId.getFileName());
        viewDto.setFileSize(byFileId.getFileSize());
        viewDto.setInputStream(provider.get(byFileId.getRelativePath()));

        //文件类型 图片和pdf的MediaType
        MediaType mediaType = covertPreviewFileMediaType(byFileId.getFileName());
        viewDto.setMediaType(mediaType);
        viewDto.setCanPreview(Boolean.FALSE);

        //图片和非Android微信的PDF支持预览
        if (Objects.nonNull(mediaType)) {
            Boolean weChatPdf = isWeChatPdf(byFileId.getFileName(), request);
            viewDto.setCanPreview(!weChatPdf);
        }
        return viewDto;
    }

    /**
     * 删除文件
     *
     * @param fileId
     */
    @Transactional
    @Override
    public void delete(Long fileId) {
        File file = fileMapper.selectByPrimaryKey(fileId);
        BusinessAssertUtils.notNull(file, "文件已被删除或不存在");
        FileMetadata fileMetadata = metadataMapper.selectByPrimaryKey(file.getMetadataId());
        metadataMapper.deleteByPrimaryKey(file.getMetadataId());
        fileMapper.deleteByPrimaryKey(fileId);
        provider.delete(fileMetadata.getRelativePath());
    }

    /**
     * 是Android微信PDF
     *
     * @param fileName
     * @param request
     * @return
     */
    private Boolean isWeChatPdf(String fileName, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        return fileName.toLowerCase().endsWith(".pdf") &&
                userAgent.contains("micromessenger") &&
                userAgent.contains("android");
    }


    /**
     * 图片和pdf的MediaType
     *
     * @param fileName
     * @return
     */
    private MediaType covertPreviewFileMediaType(String fileName) {
        if (fileName.toUpperCase().endsWith(".JPG")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.toUpperCase().endsWith(".PNG")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.toUpperCase().endsWith(".JPEG")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.toUpperCase().endsWith(".PDF")) {
            return MediaType.APPLICATION_PDF;
        } else {
            return null;
        }
    }


    /**
     * 生成预览url
     *
     * @param fileId         是否有时效性
     * @param fileExt        文件后缀
     * @param validityEnable 是否有时效性
     * @return
     */
    private String cratePreviewUrl(Long fileId, String fileExt, Boolean validityEnable) {
        StringBuilder viewUrlBuilder = new StringBuilder();
        viewUrlBuilder.append("/anon/file/view.").append(fileExt);
        if (validityEnable) {
            String authorityId = this.getValidityAuthorityId(fileId);
            viewUrlBuilder.append("?authorityId=").append(authorityId);
        } else {
            viewUrlBuilder.append("?fileId=").append(fileId);
        }
        return viewUrlBuilder.toString();
    }

    /**
     * 生成预览权限id
     *
     * @return
     */
    private String getValidityAuthorityId(Long fileId) {
        String authorityId = UUID.randomUUID().toString();
        String previewUrlAuthKey = RedisKeyConstant.getPreviewUrlAuthKey(authorityId);
        stringRedisTemplate.opsForValue().set(previewUrlAuthKey, String.valueOf(fileId), fileProperties.getValidityTime(), TimeUnit.SECONDS);
        return authorityId;
    }

    /**
     * 生成文件元数据信息
     *
     * @return
     */
    private FileMetadata createFileMetaData(FileUploadRequestDto uploadRequestDto) {
        MultipartFile file = uploadRequestDto.getFile();
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
        FileMetadata metaData = new FileMetadata();
        metaData.setId(SnowFlakeUtils.getNextId());
        metaData.setFileName(originalFilename);
        metaData.setFileSize(file.getSize());
        metaData.setFileExt(FilenameUtils.getExtension(originalFilename));
        return metaData;
    }
}
