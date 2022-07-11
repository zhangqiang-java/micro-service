package com.zq.cloud.authuser.core.constant;

public class RedisKeyConstant {

    private static final String USER_TOKEN_KEY = "USER_TOKEN:%s";


    // 用户token
    public static String getUserTokenKey(String token) {
        return String.format(USER_TOKEN_KEY, token);
    }
}
