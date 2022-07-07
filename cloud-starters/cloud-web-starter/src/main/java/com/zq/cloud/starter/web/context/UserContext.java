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

        //todo 封装传输信息
    }
}
