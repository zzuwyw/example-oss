package com.example.oss.common.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Failure implements Result, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Boolean status = Boolean.FALSE;
    private Integer errorCode;
    private String errorMessage;

    /**
     * 请求失败，返回错误信息
     * @param errorCode 错误码
     * @param errorMessage 错误描述
     * @return 错误信息
     */
    public static Failure failure(int errorCode, String errorMessage) {
        return new Failure().setErrorCode(errorCode).setErrorMessage(errorMessage);
    }

    /**
     * 请求失败，返回json格式的错误信息
     * @param errorCode 错误码
     * @param errorMessage 错误描述
     * @return json格式的错误信息
     * @throws JsonProcessingException json处理异常
     */
    public static String failureAsString(int errorCode, String errorMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Failure.failure(errorCode, errorMessage));
    }

}
