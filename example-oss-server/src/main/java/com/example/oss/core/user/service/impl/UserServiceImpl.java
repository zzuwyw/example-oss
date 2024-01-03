package com.example.oss.core.user.service.impl;

import com.example.oss.common.exception.ServiceException;
import com.example.oss.core.user.domain.entity.User;
import com.example.oss.core.user.domain.request.UserAddRequest;
import com.example.oss.core.user.domain.request.UserSelectRequest;
import com.example.oss.core.user.domain.response.UserResponse;
import com.example.oss.core.user.mapper.UserMapper;
import com.example.oss.core.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isBlank()) {
            throw new UsernameNotFoundException("用户名不能空");
        }
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .roles("ADMIN")
                .disabled(!user.getEnabled())
                .build();
    }

    @Override
    public UserResponse addUser(UserAddRequest request) {
        String username = request.getUsername();
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            throw new ServiceException(1000, "用户名已存在");
        }
        PasswordEncoder passwordEncoder = passwordEncoder();
        String password = passwordEncoder.encode(request.getPassword());

        user = new User();
        Date registerAt = new Date();
        user
                .setUsername(username)
                .setPassword(password)
                .setRealName(request.getRealName())
                .setEnabled(true)
                .setRegisterAt(registerAt);

        userMapper.insertUser(user);

        UserResponse response = new UserResponse();
        response
                .setUsername(username)
                .setRealName(request.getRealName())
                .setRegisterAt(registerAt)
                .setEnabled(true);

        return response;
    }

    @Override
    public List<UserResponse> getUserList(UserSelectRequest request) {

        List<User> userList = userMapper.getUserList(request);
        if (!CollectionUtils.isEmpty(userList)) {
            List<UserResponse> list = new ArrayList<>(userList.size());
            userList.forEach(i -> {
                UserResponse user = new UserResponse();
                user
                        .setUsername(i.getUsername())
                        .setRealName(i.getRealName())
                        .setRegisterAt(i.getRegisterAt())
                        .setEnabled(i.getEnabled());
                list.add(user);
            });
            return list;
        }
        return null;
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
