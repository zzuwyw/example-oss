package com.example.oss.core.user.service;

import com.example.oss.core.user.domain.response.Me;

public interface AuthenticationService {

    Me me(String username);

}
