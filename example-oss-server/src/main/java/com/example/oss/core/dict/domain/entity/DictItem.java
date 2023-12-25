package com.example.oss.core.dict.domain.entity;

import lombok.Data;

@Data
public class DictItem {
    private Integer id;
    private String dictCode;
    private String itemCode;
    private String itemName;
    private Integer sort;
}
