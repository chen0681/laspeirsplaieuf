package com.test.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private String userName;

    private String userPwd;

    private Integer age;
}
