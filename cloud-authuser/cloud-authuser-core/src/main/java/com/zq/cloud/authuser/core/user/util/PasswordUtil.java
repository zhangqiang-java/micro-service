package com.zq.cloud.authuser.core.user.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 密码加密工具类
 */
public class PasswordUtil {


    /**
     * 生成含有随机盐的密码
     */
    public static String generate(String password) {
        return generate(password, getRandomSalt());
    }

    /**
     * 生成指定盐的密码
     */
    public static String generate(String password, String randomSalt) {
        password = md5Hex(password + randomSalt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = randomSalt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 生成16位随机盐
     */
    public static String getRandomSalt() {
        Long saltLong = (long) (Math.random() * (10000000000000000L));
        return String.format("%016d", saltLong);
    }


    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

}
