package com.fxz.monitor.server.env;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Service(version = "1.0.0")
public class AppAware implements ApplicationContextAware {
    private static final String propertyName = "appAware-properties";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Properties properties = new Properties();
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(propertyName, properties);
        properties.put("test.myKey", "appAware-properties");
        ((ConfigurableEnvironment) applicationContext.getEnvironment()).getPropertySources().addFirst(propertiesPropertySource);
    }
}
