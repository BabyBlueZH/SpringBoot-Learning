package com.example.learnspringboot;

/**
 * 自定义业务异常
 * <p>
 * 当业务逻辑出问题时（比如"学生不存在"、"用户名已存在"），
 * 就 throw 这个异常，全局异常处理器会自动把它转成统一的 Result 返回。
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 快捷构造：默认状态码 400（参数/业务错误）
     */
    public BusinessException(String message) {
        this(400, message);
    }

    public int getCode() {
        return code;
    }
}
