package com.example.learnspringboot;

/**
 * 统一返回结构
 * <p>
 * 所有 API 接口都返回这个格式，前端就不用每种响应都单独处理了。
 *
 * @param <T> data 的类型
 */
public class Result<T> {

    private int code;       // 业务状态码（200=成功，其他=失败）
    private String message; // 提示信息
    private T data;         // 真正的返回数据

    // ============ 构造方法（私有，不让外部 new） ============

    private Result() {
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ============ 静态工厂方法（推荐用这些创建 Result） ============

    /**
     * 成功：有返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 成功：无返回数据（比如删除成功）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    /**
     * 失败：自定义状态码和消息
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败：400 参数错误
     */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }

    /**
     * 失败：404 资源不存在
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(404, message, null);
    }

    /**
     * 失败：500 服务器内部错误
     */
    public static <T> Result<T> serverError(String message) {
        return new Result<>(500, message, null);
    }

    // ============ getter / setter ============

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
