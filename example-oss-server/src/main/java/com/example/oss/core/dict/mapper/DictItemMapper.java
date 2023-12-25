package com.example.oss.core.dict.mapper;

import com.example.oss.core.dict.domain.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictItemMapper {

    List<DictItem> getAll();

    List<DictItem> getDictItemList(@Param("dictCode") String dictCode);
}
