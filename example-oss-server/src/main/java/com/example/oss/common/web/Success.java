package com.example.oss.common.web;

import lombok.Data;

@Data
public class Success {
    private Integer code = 200;
    private Object data;
    private String message = "ok";

    private Success(Object data) {
        this.data = data;
    }

    public static Success ok() {
        return Success.ok(true);
    }

    public static Success ok(Object data) {
        return new Success(data);
    }

}
