package com.zq.cloud.utils;

import com.zq.cloud.dto.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密工具类
 */
@Slf4j
public class RSAEncryptUtil {

    //加密算法
    public final static String RSA = "RSA";
    //签名算法
    public static final String SIGN_ALGORITHMS = "SHA256withRSA";
    //签名摘要
    public static final String DIGEST = "SHA-256";
    //参考https://cloud.tencent.com/developer/article/1199963 具体参考
    //注意密钥长度 和加密明文，解密密文的关系
    //密钥大小
    public static final int SIZE = 2048;
    //RSA分段加密明文大小
    private static final int ENCRYPT_BLOCK = SIZE / 8 - 11;
    //RSA分段解密密文大小
    private static final int DECRYPT_BLOCK = SIZE / 8;

    private static final String ENCODE = "UTF-8";

    public static final String PUBLIC_KEY = "PublicKey";

    public static final String PRIVATE_KEY = "PrivateKey";


    /**
     * 随机生成密钥对 key 公钥 value 秘钥
     */
    public static Map<String, String> genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
            // 初始化密钥对生成器，密钥大小为96-2048位
            keyPairGen.initialize(SIZE);
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到公钥字符串
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            Map<String, String> keyData = new HashMap<>();
            keyData.put(PUBLIC_KEY, publicKeyString);
            keyData.put(PRIVATE_KEY, privateKeyString);
            return keyData;
        } catch (Exception e) {
            throw new IllegalArgumentException("秘钥生成失败:" + e.getMessage());
        }
    }

    /**
     * 获得公钥
     *
     * @param publicKeyStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static RSAPublicKey getRSAPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);

    }

    /**
     * 获得私钥
     *
     * @param privateKeyStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static RSAPrivateKey getRSAPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     * 1.通过Base64解压公钥字符串,获得公钥对象
     * 2.utf-8的方式获得数据的字节数组,
     * 3.RSA方式对数据字节数组分段加密
     * 4.获得加密后的字节数组 通过Base64压缩为字符串
     *
     * @param publicKeyStr
     * @param clearText
     * @return
     * @throws BusinessException
     */
    public static String encryptByPublicKey(String publicKeyStr, String clearText) {
        try {
            RSAPublicKey publicKey = getRSAPublicKey(publicKeyStr);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] sourceData = clearText.getBytes(ENCODE);
            byte[] encryptedData = dateSplitHandle(cipher, sourceData, ENCRYPT_BLOCK);
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (NoSuchAlgorithmException e) {
            log.warn("加密失败:无此算法", e);
            throw new BusinessException("加密失败");
        } catch (InvalidKeySpecException e) {
            log.warn("加密失败:获得公钥非法", e);
            throw new BusinessException("加密失败");
        } catch (NoSuchPaddingException e) {
            log.warn("加密失败:获得RSA的密码对象失败", e);
            throw new BusinessException("加密失败");
        } catch (InvalidKeyException e) {
            log.warn("加密失败:加载公钥非法", e);
            throw new BusinessException("加密失败");
        } catch (UnsupportedEncodingException e) {
            log.warn("加密失败:转换原始数据为字节时失败", e);
            throw new BusinessException("加密失败");
        } catch (BadPaddingException e) {
            log.warn("加密失败:明文数据已损坏", e);
            throw new BusinessException("加密失败");
        } catch (IllegalBlockSizeException e) {
            log.warn("加密失败:明文长度非法", e);
            throw new BusinessException("加密失败");
        } catch (Exception e) {
            log.warn("加密失败:未知原因", e);
            throw new BusinessException("加密失败");
        }
    }


    /**
     * 私钥解密
     * 1.通过Base64解压私钥字符串,获得私钥对象
     * 2.通过Base64解压数据字符串,获得数据字节数组
     * 3.RSA方式对数据字节数组分段解密，得到解密后的数据字节数组
     * 4.utf-8方式还原数据为字符串
     *
     * @param privateKeyStr
     * @param cipherText
     * @return
     * @throws BusinessException
     */
    public static String decryptByPrivateKey(String privateKeyStr, String cipherText) {
        try {
            RSAPrivateKey privateKey = getRSAPrivateKey(privateKeyStr);
            byte[] encryptedData = Base64.getDecoder().decode(cipherText);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = dateSplitHandle(cipher, encryptedData, DECRYPT_BLOCK);
            return new String(decryptedData, ENCODE);
        } catch (NoSuchAlgorithmException e) {
            log.warn("解密失败:无此算法", e);
            throw new BusinessException("解密失败");
        } catch (InvalidKeySpecException e) {
            log.warn("解密失败:获得私钥非法", e);
            throw new BusinessException("解密失败");
        } catch (NoSuchPaddingException e) {
            log.warn("解密失败:获得RSA的密码对象失败", e);
            throw new BusinessException("解密失败");
        } catch (InvalidKeyException e) {
            log.warn("解密失败:加载私钥非法", e);
            throw new BusinessException("解密失败");
        } catch (IllegalBlockSizeException e) {
            log.warn("解密失败:密文长度非法", e);
            throw new BusinessException("解密失败");
        } catch (BadPaddingException e) {
            log.warn("解密失败:密文数据已损坏", e);
            throw new BusinessException("解密失败");
        } catch (UnsupportedEncodingException e) {
            log.warn("解密失败:转换解密后的数据字节为字符串时失败", e);
            throw new BusinessException("解密失败");
        } catch (Exception e) {
            log.warn("解密失败:未知原因", e);
            throw new BusinessException("解密失败");
        }
    }


    /**
     * 私钥签名
     *
     * @param content
     * @param privateKeyStr
     * @return
     * @throws BusinessException
     */
    public static String signByPrivateKey(String content, String privateKeyStr) {
        try {
            //对content使用"DIGEST"算法进行摘要计算得到strHash
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST);
            messageDigest.update(content.getBytes(ENCODE));
            byte[] strHash = messageDigest.digest();
            //使用私钥和SHA256withRSA算法对strHash计算签名，得到signByte
            RSAPrivateKey privateKey = getRSAPrivateKey(privateKeyStr);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(strHash);
            byte[] signByte = signature.sign();
            //将signByte字节数组转为十六进制字符串sign
            return DatatypeConverter.printHexBinary(signByte);
        } catch (NoSuchAlgorithmException e) {
            log.warn("签名失败：无此算法", e);
            throw new BusinessException("签名失败");
        } catch (InvalidKeySpecException e) {
            log.warn("签名失败：获得私钥非法", e);
            throw new BusinessException("签名失败");
        } catch (InvalidKeyException e) {
            log.warn("签名失败：加载私钥非法", e);
            throw new BusinessException("签名失败");
        } catch (UnsupportedEncodingException e) {
            log.warn("签名失败：转换原始数据为字节时失败", e);
            throw new BusinessException("签名失败");
        } catch (SignatureException e) {
            log.warn("签名失败：签名失败", e);
            throw new BusinessException("签名失败");
        } catch (Exception e) {
            log.warn("签名失败：未知原因", e);
            throw new BusinessException("签名失败");
        }
    }

    /**
     * 公钥签名验证
     *
     * @param content
     * @param sign
     * @param publicKeyStr
     * @return
     * @throws BusinessException
     */
    public static boolean signVerifyByPublicKey(String content, String sign, String publicKeyStr) {
        try {
            //对content使用"DIGEST"算法进行摘要计算得到strHash
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST);
            messageDigest.update(content.getBytes(ENCODE));
            byte[] strHash = messageDigest.digest();
            //使用公钥和SHA256withRSA算法，对strHash进行验签计算
            RSAPublicKey publicKey = getRSAPublicKey(publicKeyStr);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(strHash);
            //将十六进制字符串sign转换为字节数组signByte,然后进行验签
            byte[] signByte = DatatypeConverter.parseHexBinary(sign);
            return signature.verify(signByte);
        } catch (NoSuchAlgorithmException e) {
            log.warn("验签失败：无此算法", e);
            throw new BusinessException("验签失败");
        } catch (InvalidKeySpecException e) {
            log.warn("验签失败：获得公钥非法", e);
            throw new BusinessException("验签失败");
        } catch (InvalidKeyException e) {
            log.warn("验签失败：加载公钥非法", e);
            throw new BusinessException("验签失败");
        } catch (UnsupportedEncodingException e) {
            log.warn("验签失败：转换原始数据为字节时失败", e);
            throw new BusinessException("验签失败");
        } catch (SignatureException e) {
            log.warn("验签失败：签名长度不够", e);
            throw new BusinessException("验签失败");
        } catch (Exception e) {
            log.warn("验签失败：未知原因", e);
            throw new BusinessException("验签失败");
        }
    }

    /**
     * 数据分段处理
     *
     * @param cipher 加密或者解密
     * @param data   数据字节
     * @param block  分段长度
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] dateSplitHandle(Cipher cipher, byte[] data, int block) throws BadPaddingException,
            IllegalBlockSizeException {
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > block) {
                cache = cipher.doFinal(data, offSet, block);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        return out.toByteArray();
    }

    public static void main(String[] args) {
        Map<String, String> key = genKeyPair();
        String publicKey = key.get(PUBLIC_KEY);
        String privateKey = key.get(PRIVATE_KEY);


        String s = "5465465465465";
        String encryStr = signByPrivateKey(s, privateKey);
        System.err.println("签名结果" + encryStr);
        boolean resut = signVerifyByPublicKey(s, encryStr, publicKey);
        System.err.println("验签结果" + resut);

        String s1 = encryptByPublicKey(publicKey, s);
        System.err.println(s1);

        String s2 = decryptByPrivateKey(privateKey, s1);
        System.err.println(s2);
    }
}
