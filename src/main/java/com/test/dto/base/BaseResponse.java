package com.test.dto.base;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse<T> implements Serializable{

    private String msg;

    private T data;
}
