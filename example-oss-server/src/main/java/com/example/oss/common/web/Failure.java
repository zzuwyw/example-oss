package com.example.oss.common.web;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Failure implements Result, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Boolean status = Boolean.FALSE;
    private String errorMessage;

    /**
     * 请求失败，返回错误信息
     * @param errorMessage 错误描述
     * @return 错误信息
     */
    public static Failure failure(String errorMessage) {
        return new Failure().setErrorMessage(errorMessage);
    }

}
