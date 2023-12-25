package com.example.oss.core.dict.service;

import com.example.oss.core.dict.domain.response.DictResponse;

public interface DictService {
    void cacheDict();

    DictResponse getDict(String dictCode);
}
