package com.example.oss.core.user.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private Boolean enabled;
    private Date registerAt;
    private Date lastLogin;

}
