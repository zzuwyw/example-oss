package com.example.oss.common.exception;

import com.example.oss.common.web.Failure;
import com.example.oss.common.web.Result;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    /**
     * 客户端请求的资源不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result requestResourceNotFoundException() {
        return Failure.failure("请求资源不存在");
    }

    /**
     * 客户端请求的 Method 不支持
     * eg.GET POST
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result requestMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        log.info("method:{}", method);
        return Failure.failure("请求的 Method 不支持");
    }

    /**
     * 客户端请求的 Content-Type 不支持
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        MediaType contentType = e.getContentType();
        log.info("contentType:{}", contentType);
        return Failure.failure("请求的 Content-Type 不支持");
    }

    /**
     * 请求入参使用 @RequestParam(required = true)限定时，参数缺失时抛出该异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Failure missingServletRequestParameterException(MissingServletRequestParameterException e) {
        String parameterName = e.getParameterName();
        log.info("parameterName:{}", parameterName);
        return Failure.failure("缺少必要的入参");
    }

    /**
     * 请求入参使用 @RequestBody限定时，如果参数体为空时会抛出该异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Failure httpMessageNotReadableException() {
        return Failure.failure("请求缺少入参参数体");
    }

    /**
     * 入参校验异常
     * MethodArgumentNotValidException 入参缺失、入参不符合预期
     */
    @ExceptionHandler({
            ValidationException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result requestParamsValidException(Exception e) {
        if (e instanceof ValidationException) {
            return Failure.failure(e.getMessage());
        } else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError == null) {
                throw new RuntimeException(e);
            }
            return Failure.failure(fieldError.getDefaultMessage());
        } else {
            throw new RuntimeException(e);
        }
    }

    /**
     * 此异常是业务异常，即请求不符合预期，响应码应返回400
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result requestServiceException(ServiceException e) {
        return Failure.failure(e.getMessage());
    }

    /**
     * 未曾预料的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result internalServerError(Exception e) {
        log.error(e.getMessage(), e); // 暂时打印日志，后面碰到具体异常再具体处理
        return Failure.failure("未知异常");
    }


}
