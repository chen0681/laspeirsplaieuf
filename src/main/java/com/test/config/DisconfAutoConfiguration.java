package com.test.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/6.
 */
//@Configurable
public class DisconfAutoConfiguration implements EnvironmentAware{

    private ConfigurableEnvironment environment;

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean getDisconfMgrBean() {
        DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
        disconfMgrBean.setScanPackage("com.test");
        return disconfMgrBean;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond getDisconfMgrBeanSecond() {
        return new DisconfMgrBeanSecond();
    }

    @Bean
    public ReloadablePropertiesFactoryBean getReloadablePropertiesFactoryBean() {
        ReloadablePropertiesFactoryBean factoryBean = new ReloadablePropertiesFactoryBean();
        factoryBean.setLocation("aa.properties");
        return factoryBean;
    }

    @Bean
    @ConditionalOnBean(ReloadablePropertiesFactoryBean.class)
    public ReloadingPropertyPlaceholderConfigurer getReloadingPropertyPlaceholderConfigurer(
            ReloadablePropertiesFactoryBean factoryBean) throws IOException {
        ReloadingPropertyPlaceholderConfigurer configurer = new ReloadingPropertyPlaceholderConfigurer();
        configurer.setProperties(factoryBean.getObject());
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        PropertiesPropertySource source = new PropertiesPropertySource("disconf.properties", factoryBean.getObject());
        environment.getPropertySources().addLast(source);
        return configurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}
