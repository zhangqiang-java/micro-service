
package com.zq.cloud.validation;

import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 没有严格限制  只考虑了格式
 * 严格校验可参考：https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md
 */
public final class MobileNoPredicate implements Predicate<String> {

    public static final Pattern VALID_MOBILE_NUMBER_REGEX = Pattern
            .compile("^1[3-9][0-9]{9}$", Pattern.CASE_INSENSITIVE);

    public static final MobileNoPredicate INSTANCE = new MobileNoPredicate();

    private MobileNoPredicate() {

    }

    @Override
    public boolean apply(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        Matcher matcher = VALID_MOBILE_NUMBER_REGEX.matcher(input);
        return matcher.find();
    }

}
