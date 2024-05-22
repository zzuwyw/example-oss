package com.example.oss.common.exception;

import com.example.oss.common.web.Failure;
import com.example.oss.common.web.Result;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static jakarta.servlet.http.HttpServletResponse.*;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public Result requestResourceNotFoundException() {
        return Failure.failure(SC_NOT_FOUND, "请求资源不存在");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result requestMethodNotAllowedException() {
        return Failure.failure(SC_METHOD_NOT_ALLOWED, "请求方式不支持");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result httpMediaTypeNotSupportedException() {
        return Failure.failure(SC_UNSUPPORTED_MEDIA_TYPE, "数据传输格式不支持");
    }

    @ExceptionHandler({
            ValidationException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result requestParamsValidException(Exception e) {
        if (e instanceof ValidationException) {
            return Failure.failure(400, e.getMessage());
        } else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            if (!bindingResult.hasErrors()) {
                return Failure.failure(400, "未知异常");
            }
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError == null) {
                return Failure.failure(400, "未知异常");
            }
            return Failure.failure(400, fieldError.getDefaultMessage());
        }
        log.error(e.getMessage(), e); // 暂时打印日志，后面碰到具体异常再具体处理
        return Failure.failure(400, "未知异常");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result requestServiceException(ServiceException e) {
        return Failure.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result internalServerError(Exception e) {
        log.error(e.getMessage(), e); // 暂时打印日志，后面碰到具体异常再具体处理
        return Failure.failure(SC_INTERNAL_SERVER_ERROR, "未知异常");
    }


}
