package com.example.oss.core.user.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserResponse {
    private String username;
    private String realName;
    private Date registerAt;
    private Boolean enabled;

}
