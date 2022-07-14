package com.zq.cloud.file.core.constant;

public class RedisKeyConstant {

    private static final String PREVIEW_URL_AUTH_KEY = "PREVIEW_URL_AUTH:%s";


    /**
     * 预览权限id  key
     *
     * @param authorityId
     * @return
     */
    public static String getPreviewUrlAuthKey(String authorityId) {
        return String.format(PREVIEW_URL_AUTH_KEY, authorityId);
    }


}
