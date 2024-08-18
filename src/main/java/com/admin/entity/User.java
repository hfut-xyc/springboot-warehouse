package com.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public final static int ROLE_USER = 0;
    public final static int ROLE_ADMIN = 1;

    private Integer id;
    private String username;
    private String password;
    private Integer role;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
