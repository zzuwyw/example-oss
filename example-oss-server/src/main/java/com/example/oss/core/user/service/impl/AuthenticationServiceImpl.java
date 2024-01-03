package com.example.oss.core.user.service.impl;

import com.example.oss.core.user.domain.entity.User;
import com.example.oss.core.user.domain.response.Principal;
import com.example.oss.core.user.mapper.UserMapper;
import com.example.oss.core.user.service.AuthenticationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Principal getPrincipal(String username) {
        Principal principal = new Principal();
        User user = userMapper.getUserByUsername(username);
        principal
                .setUsername(username)
                .setRegisterAt(user.getRegisterAt());
        return principal;
    }
}
