package com.example.oss.common.exception;

import com.example.oss.common.web.Failure;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Failure requestResourceNotFoundException() {
        return new Failure(404, "请求资源不存在");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Failure requestMethodNotAllowedException() {
        return new Failure(405, "请求方式不支持");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Failure httpMediaTypeNotSupportedException() {
        return new Failure(415, "数据传输格式不支持");
    }

    @ExceptionHandler({
            ValidationException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Failure requestParamsValidException(Exception e) {
        if (e instanceof ValidationException) {
            return new Failure(400, e.getMessage());
        } else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            if (!bindingResult.hasErrors()) return new Failure(400, "未知异常");
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError == null) return new Failure(400, "未知异常");
            return new Failure(400, fieldError.getDefaultMessage());
        }
        log.error(e.getMessage(), e); // 暂时打印日志，后面碰到具体异常再具体处理
        return new Failure(400, "未知异常");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Failure requestServiceException(ServiceException e) {
        return new Failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Failure internalServerError(Exception e) {
        log.error(e.getMessage(), e); // 暂时打印日志，后面碰到具体异常再具体处理
        return new Failure(500, "未知异常");
    }







}
