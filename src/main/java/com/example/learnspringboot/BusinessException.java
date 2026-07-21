package com.example.learnspringboot;
//这是一个"带状态码的异常"。以前 throw 只能传一句话，
// 现在能传 code + message。

//用的时候：throw new BusinessException(404, "学生不存在")

/**
 * 自定义业务异常
 * <p>
 * 当业务逻辑出问题时（比如"学生不存在"、"用户名已存在"），
 * 就 throw 这个异常，全局异常处理器会自动把它转成统一的 Result 返回。
 */
public class BusinessException extends RuntimeException {

    private final int code;// 业务状态码，比如 404、400

    public BusinessException(int code, String message) {
        super(message);// message 传给父类RuntimeException
        this.code = code;
    }

    /**
     * 快捷构造：默认状态码 400（参数/业务错误）
     */
    public BusinessException(String message) {

        this(400, message);  // 不传 code 时默认 400
    }

    public int getCode() {
        return code;
    }
}
