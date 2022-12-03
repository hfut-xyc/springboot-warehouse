package org.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean status;
    private Boolean admin;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
