package com.example.oss.core.user.controller;

import com.example.oss.common.web.Success;
import com.example.oss.core.user.domain.request.UserAddRequest;
import com.example.oss.core.user.domain.request.UserSelectRequest;
import com.example.oss.core.user.domain.response.UserResponse;
import com.example.oss.core.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/getUserList")
    public Success getUserList(UserSelectRequest request) {
        List<UserResponse> userList = userService.getUserList(request);
        return Success.ok(userList);
    }

    @RequestMapping("/addUser")
    public Success addUser(UserAddRequest request) {
        UserResponse response = userService.addUser(request);
        return Success.ok(response);
    }


}
