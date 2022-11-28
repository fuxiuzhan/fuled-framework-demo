package com.fxz.monitor.server.env;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppAware implements ApplicationContextAware {
    private static final String propertyName = "appAware-properties";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map hashMap = new HashMap();
        MapPropertySource mapPropertySource = new MapPropertySource(propertyName, hashMap);
        hashMap.put("test.myKey", "appAware-properties");
        ((ConfigurableEnvironment) applicationContext.getEnvironment()).getPropertySources().addFirst(mapPropertySource);
    }
}
