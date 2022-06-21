package com.zq.cloud.constant;

/**
 * 静态常量
 */
public class StaticFinalConstant {

    /**
     * 未登录
     */
    public static final String NOT_LOGIN_CODE = "NOT_LOGIN";

    /**
     * 对外暴露异常提示语
     */
    public static final String OPEN_ERROR_MESSAGE = "系统繁忙，请稍后再试";


    /**
     * 错误sql语法
     */
    public static final String BAD_SQL_GRAMMAR_EXCEPTION = "org.springframework.jdbc.BadSqlGrammarException";


    /**
     * 违法数据库唯一约束
     */
    public static final String DUPLICATE_KEY_EXCEPTION = "org.springframework.dao.DuplicateKeyException";


    /**
     * 数据库错误
     */
    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION = "org.springframework.dao.DataIntegrityViolationException";

    /**
     * Mybatis错误
     */
    public static final String MYBATIS_SYSTEM_EXCEPTION = "org.mybatis.spring.MyBatisSystemException";


    /**
     * IO 异常
     */
    public static final String IO_EXCEPTION = "java.io.IOException";


    /**
     * 链接异常
     */
    public static final String CONNECT_EXCEPTION = "java.net.ConnectException";


    /**
     * socket异常
     */
    public static final String SOCKET_EXCEPTION = "java.net.SocketException";

    /**
     * 超时异常
     */
    public static final String SOCKET_TIMEOUT_EXCEPTION = "java.net.SocketTimeoutException";



    /**
     * 非法参数
     */
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "java.lang.IllegalArgumentException";


    /**
     * 参数类型错误
     */
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException";


    /**
     *方法参数无效异常
     */
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "org.springframework.web.bind.MethodArgumentNotValidException";

    /**
     * 参数验证异常
     */
    public static final String VALIDATION_EXCEPTION = "javax.validation.ValidationException";

    /**
     * 参数约束违反异常
     */
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "javax.validation.ConstraintViolationException";

    /**
     * 超出最大上传大小异常
     */
    public static final String MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION = "org.springframework.web.multipart.MaxUploadSizeExceededException";












}
