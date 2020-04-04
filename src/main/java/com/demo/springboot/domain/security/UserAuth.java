package com.demo.springboot.domain.security;

import lombok.Data;

@Data
public class UserAuth {
    private Integer id;

    private Integer userId;

    private Integer authId;

    private String loginName;

    private String userPassword;

    private String authority;
}