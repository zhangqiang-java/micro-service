package com.zq.cloud.file.core.provider.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.zq.cloud.file.core.config.FileProperties;
import com.zq.cloud.file.core.config.OssClientBuilderConfiguration;
import com.zq.cloud.file.core.provider.StorageProvider;
import com.zq.cloud.file.core.provider.oss.dto.OssCallBackResultDto;
import com.zq.cloud.file.core.provider.oss.dto.OssCallbackParam;
import com.zq.cloud.file.core.provider.oss.dto.OssPolicyResult;
import com.zq.cloud.file.core.provider.oss.dto.OssStaticParameters;
import com.zq.cloud.file.core.provider.oss.listener.OssGetProgressListener;
import com.zq.cloud.file.core.provider.oss.listener.OssUploadProgressListener;
import com.zq.cloud.file.dal.mapper.FileMapper;
import com.zq.cloud.file.dal.mapper.FileMetadataMapper;
import com.zq.cloud.file.dal.model.File;
import com.zq.cloud.file.dal.model.FileMetadata;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 阿里云oss提供存储服务
 */
@Slf4j
public class OssStorageProvider implements StorageProvider {

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private OssClientBuilderConfiguration ossConfiguration;

    @Autowired
    private FileProperties fileProperties;


    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileMetadataMapper metadataMapper;

    private final static String OSS_CALL_BACK_URL = "/anon/oss/file/callback";

    /**
     * 上传文件
     *
     * @param inputStream
     * @param metaData
     */
    @Override
    public void upload(InputStream inputStream, FileMetadata metaData) {
        String storePath = this.createStorePath(metaData.getFileExt());
        this.uploadFile(storePath, inputStream);
        metaData.setRelativePath(storePath);
    }


    /**
     * 获取文件
     *
     * @param providerId
     */
    @Override
    public InputStream get(String providerId) {
        OSSObject ossObject = this.getObject(providerId);
        return ossObject.getObjectContent();
    }

    /**
     * 删除文件
     *
     * @param providerId
     */
    @Override
    public void delete(String providerId) {
        this.deleteObject(providerId);
    }


    /**
     * 签名生成
     */
    public OssPolicyResult policy() {
        log.info("进入签名生成方法");

        OssPolicyResult result = new OssPolicyResult();
        try {
            // 签名有效期
            long expireEndTime = System.currentTimeMillis() + ossConfiguration.getExpireTime() * 1000;
            Date expiration = new java.util.Date(expireEndTime);

            // policy生成
            PolicyConditions policyCons = new PolicyConditions();
            String directory = this.createStoreDirectory();
            policyCons.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, directory);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyCons);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String policy = BinaryUtil.toBase64String(binaryData);

            // 签名生成
            String signature = ossClient.calculatePostSignature(postPolicy);

            //  host
            String host = "http://" + ossConfiguration.getBucketName() + "." + ossConfiguration.getEndpoint();

            //callbackBody参数
            OssCallbackParam callbackParam = new OssCallbackParam();
            callbackParam.setCallbackUrl(fileProperties.getPreviewDomainName() + OSS_CALL_BACK_URL);
            callbackParam.setCallbackBody(OssStaticParameters.BASE_CALL_BACK_BODY);


            // 组装返回结果
            Credentials credentials = ossClient.getCredentialsProvider().getCredentials();
            result.setOSSAccessKeyId(credentials.getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setHost(host);
            result.setCallback(callbackParam);
            result.setDirectory(directory);

        } catch (Exception e) {
            log.error("获取签名失败", e);
            BusinessAssertUtils.fail("获取签名失败");
        }
        return result;
    }

    /**
     * 前端直传 oss 回调处理
     *
     * @param request
     */
    public void callback(HttpServletRequest request) {
        log.info("oss成功发起上传回调");
        OssCallBackResultDto resultDto = new OssCallBackResultDto();
        String fullPath = "";
        try {
            // 1.获取前端携带的参数信息
            String ossCallbackBody = this.getCallbackPostBody(request.getInputStream(),
                    Integer.parseInt(request.getHeader(OssStaticParameters.CONTENT_LENGTH)));
            String callParamStr = URLDecoder.decode(ossCallbackBody, "UTF-8");
            log.info("oss携带的参数为：{}", callParamStr);


            // 2.校验是否为oss调用
            verifyOSSCallbackRequest(request, ossCallbackBody);
            log.info("校验确定为oss调用");

            // 3.解析body参数
            Map<String, String> callParamMap = extractedParamMap(callParamStr);


            // 4.断言必要参数
            fullPath = callParamMap.get("object");
            BusinessAssertUtils.isTrue(StringUtils.isNotBlank(fullPath), "文件存储路径不能为空");
            String size = callParamMap.get("size");
            BusinessAssertUtils.isTrue(StringUtils.isNotBlank(size), "文件大小不能为空");
            String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
            String extension = FilenameUtils.getExtension(fileName);

            //自定义参数
            String systemCode = callParamMap.get("systemCode");
            String bizCode = callParamMap.get("bizCode");


            //数据持久化
            FileMetadata metaData = new FileMetadata();
            metaData.setId(SnowFlakeUtils.getNextId());
            metaData.setFileName(fileName);
            metaData.setFileSize(Long.parseLong(size));
            metaData.setFileExt(extension);
            metadataMapper.insert(metaData);
            com.zq.cloud.file.dal.model.File file = new File();
            file.setId(SnowFlakeUtils.getNextId());
            file.setSystemCode(systemCode);
            file.setBizCode(bizCode);
            file.setMetadataId(metaData.getId());
            fileMapper.insert(file);
        } catch (Exception e) {
            log.error("oss上传回调异常", e);
            BusinessAssertUtils.fail("上传回调异常");
        }
    }


    /**
     * 上传
     *
     * @param storePath   oss文件存储路径
     * @param inputStream
     */
    private PutObjectResult uploadFile(String storePath, InputStream inputStream) {
        PutObjectResult putObjectResult = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfiguration.getBucketName(), storePath, inputStream).withProgressListener(new OssUploadProgressListener(storePath));
            putObjectResult = ossClient.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("oss 上传文件:" + storePath + "异常", e);
            BusinessAssertUtils.fail("oss 上传文件异常");
        }
        return putObjectResult;
    }


    /**
     * 下载
     *
     * @param key
     */
    private OSSObject getObject(String key) {

        OSSObject ossObject = null;
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(ossConfiguration.getBucketName(), key).withProgressListener(new OssGetProgressListener(key));
            ossObject = ossClient.getObject(getObjectRequest);
        } catch (Exception e) {
            log.error("oss 下载文件:" + key + "异常", e);
            BusinessAssertUtils.fail("oss 下载文件异常");
        }
        return ossObject;
    }

    /**
     * 删除
     *
     * @param key
     */
    private void deleteObject(String key) {
        try {
            ossClient.deleteObject(ossConfiguration.getBucketName(), key);
        } catch (Exception e) {
            log.error("oss 删除文件:" + key + "异常", e);
            BusinessAssertUtils.fail("oss 删除文件异常");
        }
    }


    /**
     * 生成文件存储目录
     *
     * @return
     */
    private String createStoreDirectory() {
        String baseFilePath = ossConfiguration.getBaseFilePath();
        if (baseFilePath.endsWith("/")) {
            baseFilePath = baseFilePath.substring(0, baseFilePath.length() - 1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        return baseFilePath + sdf.format(new Date());
    }


    /**
     * 生成文件存储路径
     *
     * @param fileExtension
     * @return
     */
    private String createStorePath(String fileExtension) {
        String storePath = this.createStoreDirectory() + UUID.randomUUID().toString().replaceAll("-", "");
        if (StringUtils.isNotBlank(fileExtension)) {
            return storePath + "." + fileExtension;
        }
        return storePath;
    }


    /**
     * 获取Post消息体
     *
     * @param is         body流
     * @param contentLen body长度
     * @return 解析后的body参数
     */
    private String getCallbackPostBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
                log.error("获取Post消息体异常，异常类型为{}，异常信息为：{}", e.getClass(), e.getMessage());
            }
        }
        return "";
    }


    /**
     * 验证上传回调的Request
     */
    private void verifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {

        // 1.获取ossPublicKey,并确保是由oss颁发的
        String ossPublicKey = getOssPublicKey(request);

        // 2.获取验证授权码
        String authorizationCode = request.getHeader(OssStaticParameters.OSS_AUTHORIZATION);
        byte[] authorization = BinaryUtil.fromBase64String(authorizationCode);

        // 3.获取组装校验内容
        String authStrContent = getAuthStrContent(request, ossCallbackBody);

        // 4.验证RSA
        boolean checkPass = doCheck(authStrContent, authorization, ossPublicKey);

        // 5.断言是由oss发起的调用
        BusinessAssertUtils.isTrue(checkPass, "上传异常", "秘钥key不是OSS颁发的");
    }


    /**
     * 获取ossPublicKey
     *
     * @return ossPublicKey
     */
    private String getOssPublicKey(HttpServletRequest request) {
        String pubKeyInput = request.getHeader(OssStaticParameters.X_OSS_PUB_KEY_URL);
        String pubKeyAddress = new String(BinaryUtil.fromBase64String(pubKeyInput));
        boolean isIssuedByOSS = pubKeyAddress.startsWith(OssStaticParameters.OSS_CHECK_RULE1)
                || pubKeyAddress.startsWith(OssStaticParameters.OSS_CHECK_RULE2);
        String ossPublicKey = executeGet(pubKeyAddress);
        ossPublicKey = ossPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        ossPublicKey = ossPublicKey.replace("-----END PUBLIC KEY-----", "");

        // 断言是oss颁发的PublicKey
        BusinessAssertUtils.isTrue(isIssuedByOSS, "上传异常", "秘钥key不是OSS颁发的");
        return ossPublicKey;
    }


    /**
     * 获取public key
     *
     * @param pubKeyAddress pubKeyAddress
     * @return public key
     */
    @SuppressWarnings({"finally"})
    public String executeGet(String pubKeyAddress) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(pubKeyAddress));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(NL);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
            log.error("获取OSS public key异常，异常类型为{}，异常信息为：{}", e.getClass(), e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    /**
     * 验证RSA
     *
     * @param content   content
     * @param sign      sign
     * @param publicKey publicKey
     * @return 校验结果
     */
    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 获取校验内容
     *
     * @param ossCallbackBody callBackBody
     * @return 校验内容
     */
    private String getAuthStrContent(HttpServletRequest request, String ossCallbackBody) throws UnsupportedEncodingException {
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String authStrContent = URLDecoder.decode(uri, "UTF-8");
        if (!com.aliyun.oss.common.utils.StringUtils.isNullOrEmpty(queryString)) {
            authStrContent += "?" + queryString;
        }
        authStrContent += "\n" + ossCallbackBody;
        return authStrContent;
    }


    /**
     * 获取body参数键值对
     *
     * @param callParamStr 原始需处理的参数
     */
    private Map<String, String> extractedParamMap(String callParamStr) {
        Map<String, String> callParamMap = new HashMap<>();
        String[] callParamStrArray = callParamStr.split("&");
        for (String callParam : callParamStrArray) {
            String[] keyValues = callParam.split("=");
            //确保是keyValue形式
            if (keyValues.length == 2) {
                callParamMap.put(keyValues[0].trim(), keyValues[1].trim());
            }
        }
        return callParamMap;
    }

}
