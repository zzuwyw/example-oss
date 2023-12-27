package com.example.oss.core.user.domain.response;

import lombok.Data;

import java.util.Date;

@Data
public class Me {
    private String username;
    private Boolean enabled;
    private Date registerAt;

}
