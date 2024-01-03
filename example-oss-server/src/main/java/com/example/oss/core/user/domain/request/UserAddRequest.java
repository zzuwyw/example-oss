package com.example.oss.core.user.domain.request;

import lombok.Data;

@Data
public class UserAddRequest {
    private String username;
    private String password;
    private String realName;

}
