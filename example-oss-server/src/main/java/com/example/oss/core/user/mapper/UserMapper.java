package com.example.oss.core.user.mapper;

import com.example.oss.core.user.domain.entity.User;
import com.example.oss.core.user.domain.request.UserSelectRequest;
import com.example.oss.core.user.domain.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserByUsername(@Param("username") String username);

    void insertUser(@Param("user") User user);

    List<User> getUserList(@Param("request") UserSelectRequest request);
}
