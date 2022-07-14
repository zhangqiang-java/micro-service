package com.zq.cloud.authuser.core.constant;

public class RedisKeyConstant {

    private static final String USER_TOKEN_KEY = "USER_TOKEN:%s";

    private static final String MOBILE_VERIFY_SEND_COUNT_KEY = "MOBILE_VERIFY_SEND_COUNT:%s";


    /**
     * 用户token  key
     * @param token
     * @return
     */
    public static String getUserTokenKey(String token) {
        return String.format(USER_TOKEN_KEY, token);
    }

    /**
     * 用户手机发送验证码次数key
     * @param mobile
     * @return
     */
    public static String getMobileVerifySendCountKey(String mobile){
        return String.format(MOBILE_VERIFY_SEND_COUNT_KEY, mobile);
    }

}
