package com.zq.cloud.utils;


import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.dto.exception.NotLoginException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BusinessAssertUtils {

    private static Logger logger = LoggerFactory.getLogger(BusinessAssertUtils.class);

    /**
     * 断言传入对象应该为空
     *
     * @param object  应该为空的对象
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void isNull(Object object, String message, Object... params) {
        if (object != null) {
            logger.error("应该为空: object={}, message={}, params={}", object, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入对象不应为空
     *
     * @param object  不应为空的对象
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void notNull(Object object, String message, Object... params) {
        if (object == null) {
            logger.error("不应为空: object={}, message={}, params={}", null, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入集合应该为空
     *
     * @param collection 应该为空的集合
     * @param message    错误原因
     * @param params     需要打印到日志中的参数，方便排查问题
     */
    public static void isEmpty(Collection<?> collection, String message, Object... params) {
        if (collection != null && collection.size() != 0) {
            logger.error("应该为空: collection={}, message={}, params={}", collection, message, params);
            throw new BusinessException(message);
        }
    }

    /**
     * 断言传入集合不应为空
     *
     * @param collection 不应为空的集合
     * @param message    错误原因
     * @param params     需要打印到日志中的参数，方便排查问题
     */
    public static void notEmpty(Collection<?> collection, String message, Object... params) {
        if (collection == null || collection.size() == 0) {
            logger.error("不应为空: collection={}, message={}, params={}", collection, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入map不应为空
     *
     * @param map     不应为空的map
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void notEmpty(Map<?, ?> map, String message, Object... params) {
        if (map == null || map.size() == 0) {
            logger.error("不应为空: map={}, message={}, params={}", map, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入字符串应该为空
     *
     * @param string  应该为空的字符串
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void isBlank(String string, String message, Object... params) {
        if (StringUtils.isNotBlank(string)) {
            logger.error("应该为空: string={}, message={}, params={}", string, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入字符串不应为空
     *
     * @param string  不应为空的字符串
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void notBlank(String string, String message, Object... params) {
        if (!StringUtils.isNotBlank(string)) {
            logger.error("不应为空: string={}, message={}, params={}", string, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入结果为true
     *
     * @param flag    为true的结果
     * @param message 错误原因
     * @param params  入参
     */
    public static void isTrue(boolean flag, String message, Object... params) {
        if (!flag) {
            logger.error("应该为TRUE: flag={}, message={},  params={}", flag, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入结果为false
     *
     * @param flag    为false的结果
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void isFalse(boolean flag, String message, Object... params) {
        if (flag) {
            logger.error("应该为FALSE: flag={}, message={}, params={}", flag, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的枚举相同
     *
     * @param actual   实际的枚举
     * @param expected 预期的枚举
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     * @param <T>
     */
    public static <T extends Enum<T>> void legalEnum(Enum<T> actual, Enum<T> expected,
                                                     String message, Object... params) {
        if (actual != expected) {
            logger.error("状态错误：message={}, actual={}, expected={}, params={}", message, actual,
                    expected, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的枚举是预期中的一个
     *
     * @param message  错误码
     * @param actual   实际的枚举
     * @param expected 预期的枚举列表
     * @param <T>
     */
    public static <T extends Enum<T>> void legalEnums(String message, Enum<T> actual,
                                                      Enum<T>... expected) {
        List<Enum<T>> enumList = Arrays.asList(expected);
        if (!enumList.contains(actual)) {
            logger.error("状态错误：message={}, actual={}, expected={}", message, actual,
                    expected);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的枚举是预期中的一个
     *
     * @param actual   实际的枚举
     * @param expected 预期的枚举列表
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     * @param <T>
     */
    public static <T extends Enum<T>> void legalEnums(Enum<T> actual,
                                                      Enum<T>[] expected, String message, Object... params) {
        List<Enum<T>> enumList = Arrays.asList(expected);
        if (!enumList.contains(actual)) {
            logger.error("状态错误：message={}, actual={}, expected={}, params={}", message, actual,
                    expected, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的字符串与预期相同
     *
     * @param actual   传入的字符串
     * @param expected 预期的字符串
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void isEquals(String actual, String expected, String message, Object... params) {
        if (!Objects.equals(actual, expected)) {
            logger.error("应该为相同：message={}, actual={}, expected={}, params={}", message, actual, expected, params);
            throw new BusinessException(message);
        }
    }

    /**
     * 断言传入的字符串与预期不同
     *
     * @param actual   传入的字符串
     * @param expected 预期的字符串
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void notEquals(String actual, String expected, String message, Object... params) {
        if (Objects.equals(actual, expected)) {
            logger.error("应该不相同：message={}, actual={}, expected={}, params={}", message, actual, expected, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的Long类型数字与预期相同
     *
     * @param actual   传入的数字
     * @param expected 预期的数字
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void isEquals(Long actual, Long expected, String message, Object... params) {
        if (!Objects.equals(actual, expected)) {
            logger.error("应该为相同：message={}, actual={}, expected={}, params={}", message, actual, expected, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入的Long类型数字与预期不同
     *
     * @param actual   传入的数字
     * @param expected 预期的数字
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void notEquals(Long actual, Long expected, String message, Object... params) {
        if (Objects.equals(actual, expected)) {
            logger.error("应该不相同：message={}, actual={}, expected={}, params={}", message, actual, expected, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言失败
     *
     * @param message 错误原因
     * @param params  需要打印到日志中的参数，方便排查问题
     */
    public static void fail(String message, Object... params) {
        logger.error("应该为失败: message={}, params={}", message, params);
        throw new BusinessException(message);
    }


    /**
     * 断言传入数组与传入字符串匹配
     *
     * @param actual   实际字符串
     * @param expected 预期字符串列表
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void isContains(String actual, Collection<String> expected, String message, Object... params) {
        if (!expected.contains(actual)) {
            logger.error("匹配错误: actual={},expected={}, message={},params={}", actual, expected, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 断言传入数组与传入字符串不匹配
     *
     * @param actual   实际字符串
     * @param expected 预期字符串列表
     * @param message  错误原因
     * @param params   需要打印到日志中的参数，方便排查问题
     */
    public static void notContains(String actual, Collection<String> expected, String message, Object... params) {
        if (expected.contains(actual)) {
            logger.error("应该为不包含: actual={},expected={}, message={},params={}", actual, expected, message, params);
            throw new BusinessException(message);
        }
    }


    /**
     * 用户未登录
     *
     * @param flag
     */
    public static void notLongin(boolean flag, String message, Object... params) {
        if (flag) {
            logger.error("用户未登录: flag={}, message={}, params={}", flag, message, params);
            throw new NotLoginException();
        }
    }

}
