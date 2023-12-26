package com.example.oss.core.user.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Date registerAt;

}
