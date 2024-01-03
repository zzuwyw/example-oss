package com.example.oss.core.user.domain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Principal {
    private String username;
    private Date registerAt;

}
