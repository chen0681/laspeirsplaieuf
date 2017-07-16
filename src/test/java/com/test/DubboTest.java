package com.test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.test.facade.IUserFacede;
import org.junit.Test;

/**
 * Created by Administrator on 2017/6/4.
 */
public class DubboTest {

    @Test
    public void testDubbo() {
        ReferenceConfig config = new ReferenceConfig();
        config.setUrl("localhost:20880");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test");
        config.setApplication(applicationConfig);
        config.setInterface(IUserFacede.class);
        config.setProtocol("dubbo");

        IUserFacede userFacede = (IUserFacede)config.get();
        String result = userFacede.getUserInfo(100L);
        System.out.println(result);
    }
}
