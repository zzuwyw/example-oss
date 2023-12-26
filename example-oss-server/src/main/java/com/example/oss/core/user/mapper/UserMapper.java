package com.example.oss.core.user.mapper;

import com.example.oss.core.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {


    User getUserByUsername(@Param("username") String username);
}
