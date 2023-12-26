package com.example.oss.common.web;

import lombok.Data;

@Data
public class Failure {
    private Integer code;
    private String message;

    public Failure(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
