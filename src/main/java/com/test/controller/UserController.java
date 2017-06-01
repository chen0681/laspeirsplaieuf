package com.test.controller;


import com.test.dto.base.BaseResponse;
import com.test.dto.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private Logger LOG = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BaseResponse<UserListResponse> list(UserListRequest request) {
        //TODO
        return null;
    }

    @PostMapping(value = "/")
    public BaseResponse<UserCreateResponse> create(UserCreateRequest request) {
        //TODO
        return null;
    }

    @CrossOrigin("*")
    @PutMapping(value = "/{id}")
    public BaseResponse<UserUpdateResponse> update(@PathVariable Long id, UserUpdateRequest request) {
        //TODO
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long id) {
        //TODO
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponse<UserGetResponse> get(@PathVariable("id") Long id) {
        //TODO
        return null;
    }


}
