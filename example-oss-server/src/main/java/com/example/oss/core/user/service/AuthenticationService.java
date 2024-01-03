package com.example.oss.core.user.service;

import com.example.oss.core.user.domain.response.Principal;

public interface AuthenticationService {

    Principal getPrincipal(String username);

}
