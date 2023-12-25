package com.example.oss.core.dict.mapper;

import com.example.oss.core.dict.domain.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictMapper {

    List<Dict> getAll();

    Dict getDict(@Param("dictCode") String dictCode);
}
