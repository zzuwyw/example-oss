package com.example.oss.core.user.controller;

import com.example.oss.common.web.Success;
import com.example.oss.core.user.domain.response.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

// 用户认证
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @GetMapping("/getPrincipal")
    public Success getPrincipal(@SessionAttribute("principal") Principal principal) {
        return Success.ok(principal);
    }

}
