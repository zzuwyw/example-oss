package com.example.oss.common.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceException extends RuntimeException {
    private Integer code;

    public ServiceException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.code = errorCode;
    }

}
