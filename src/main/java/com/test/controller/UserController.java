package com.test.controller;


import com.test.dto.base.BaseResponse;
import com.test.dto.user.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<UserListResponse> list(UserListRequest request) {
        //TODO
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public BaseResponse<UserCreateResponse> create(UserCreateRequest request) {
        //TODO
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseResponse<UserUpdateResponse> update(@PathVariable("id") Long id, UserUpdateRequest request) {
        //TODO
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable("id") Long id) {
        //TODO
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse<UserGetResponse> get(@PathVariable("id") Long id) {
        //TODO
    }


}
