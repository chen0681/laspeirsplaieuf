package com.test.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */
@Configurable
public class WebConfiguration extends WebMvcConfigurationSupport{
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }
}
