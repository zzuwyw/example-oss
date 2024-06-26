package com.example.oss.core.dict.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DictRequest {

    @NotBlank(message = "40000-dictCode不能空")
    private String dictCode;

}
