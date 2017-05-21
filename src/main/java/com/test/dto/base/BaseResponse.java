package com.test.dto.base;

import lombok.Data;


@Data
public abstract class BaseResponse<T> {

    private String msg;

    private T data;
}
