package com.example.oss.common.web;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 请求成功
 */
@Data
@Accessors(chain = true)
public class Success implements Result {
    private final Boolean status = Boolean.TRUE;
    private Object data;

    /**
     * 请求成功，无响应数据时，返回无响应数据的请求结果
     * @return 请求结果
     */
    public static Success ok() {
        return Success.ok(null);
    }

    /**
     * 请求成功，返回请求结果
     * @param data 响应数据
     * @return 请求结果
     */
    public static Success ok(Object data) {
        return new Success().setData(data);
    }

}
