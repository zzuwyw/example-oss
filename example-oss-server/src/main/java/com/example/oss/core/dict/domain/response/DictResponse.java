package com.example.oss.core.dict.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class DictResponse {
    private String dictCode;
    private String dictName;
    private List<DictItemResponse> itemList;

    @Data
    public static class DictItemResponse {
        private String itemCode;
        private String itemName;
    }

}
