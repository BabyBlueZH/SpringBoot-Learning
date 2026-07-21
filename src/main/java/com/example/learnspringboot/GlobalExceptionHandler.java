package com.example.learnspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 统一拦截所有 Controller 抛出的异常，返回 {@link Result} 格式。
 * 这样前端收到的错误信息也是统一结构，不用每种异常单独处理。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常 —— 自己抛的，有明确的 code 和 message
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验失败 —— @Valid 校验不通过时会抛出
     * 把每个字段的错误拼接成一条消息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.badRequest(msg);
    }

    /**
     * 兜底 —— 没被上面任何方法接住的异常都走这里
     * 只返回"服务器内部错误"，不暴露堆栈给前端
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.serverError("服务器内部错误");
    }
}
