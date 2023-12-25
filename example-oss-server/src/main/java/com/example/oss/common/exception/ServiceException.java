package com.example.oss.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private Integer code;
    private String message;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
