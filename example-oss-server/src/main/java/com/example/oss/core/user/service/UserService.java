package com.example.oss.core.user.service;

import com.example.oss.core.user.domain.request.UserAddRequest;
import com.example.oss.core.user.domain.request.UserSelectRequest;
import com.example.oss.core.user.domain.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponse addUser(UserAddRequest request);

    List<UserResponse> getUserList(UserSelectRequest request);
}
