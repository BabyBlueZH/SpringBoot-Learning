package com.example.learnspringboot;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

//执行流程：
//
//请求进来 → Controller 报错
//  ↓
//抛出 BusinessException(404, "学生不存在")
//  ↓
//GlobalExceptionHandler 扫描到 @ExceptionHandler(BusinessException.class) 匹配
//  ↓
//返回 Result(code=404, message="学生不存在")

/**
 * 全局异常处理器
 * <p>
 * 统一拦截所有 Controller 抛出的异常，返回 {@link Result} 格式。
 * 这样前端收到的错误信息也0.是统一结构，不用每种异常单独处理。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常 —— 自己抛的，有明确的 code 和 message
     * 现在的 GlobalExceptionHandler 返回的是 Result<Void>，状态码永远 200（因为 Result 对象本身不带 HTTP 状态码）
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) {

        return Result.error(e.getCode(), e.getMessage());
        // 把 BusinessException 转成 Result(code, message, null)
    }

//    /**
//     * 404 —— 请求的接口不存在
//     * ⚠️ 此方法已暂停：Spring Boot 4.x 废弃了 throw-exception-if-no-handler-found 属性
//     * 后续学完错误处理再补上
//     */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Result<Void> handleNotFound(NoHandlerFoundException e) {
//        return Result.error(404, "接口不存在: " + e.getRequestURL());
//    }
//
/**
     * 参数校验失败 —— @Valid
    校验不通过时会抛出
     * 把每个字段的错误拼接成一条消息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        // 把每个字段的错误拼成： "name: 姓名不能为空; score: 分数不能为空"
        return Result.badRequest(msg);
    }

    /**
     * 兜底 —— 没被上面任何方法接住的异常都走这里
     * 只返回"服务器内部错误"，不暴露堆栈给前端
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常", e);// 打印堆栈给开发者看
        return Result.serverError("服务器内部错误");// 不暴露堆栈给前端
    }
}
