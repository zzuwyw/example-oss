package com.example.oss.core.user.controller;

import com.example.oss.core.user.domain.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @RequestMapping("/me")
    public User me() {
        return null;
    }






}
