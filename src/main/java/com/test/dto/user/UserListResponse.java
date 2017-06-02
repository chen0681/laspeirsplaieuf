package com.test.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class UserListResponse implements Serializable {

    private List<UserDto> data;
}
