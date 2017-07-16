package com.test.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.test.facade.IUserFacede;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Consumer;


@Service
public class UserFacedeImpl implements IUserFacede {

    @Override
    public String getUserInfo( Long id) {
        final int num = 1;
        Converter<Integer, String> stringConverter =  (from) ->
        {
            String.valueOf(from + num);
            return "1";
        };

        Consumer<Integer> forConsumer = (from) -> {
            String.valueOf(from + num);
        };

        return String.valueOf(id);
    }
}
