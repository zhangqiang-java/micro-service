package com.zq.cloud.starter.web.context;

import com.zq.cloud.dto.DtoBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserContext {

    private static final ThreadLocal<UserContext> LOCAL = ThreadLocal.withInitial(UserContext::new);

    public static UserContext get() {
        return LOCAL.get();
    }

    public static void clear() {
        LOCAL.remove();
    }


    @Getter
    @Setter
    private CurrentUser user;

    @Setter
    @Getter
    @ToString
    public static class CurrentUser extends DtoBase {

        /**
         * 用户id
         */
        private Long id;
        /**
         * 用户昵称
         */
        private String nickname;


        /**
         * 手机号
         */
        private String mobile;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 真实姓名
         */
        private String realName;

        /**
         * 是否为管理员
         */
        private Boolean isAdmin;
    }
}
