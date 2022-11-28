package com.fxz.monitor.server.env;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fxz
 */
public class AppContextInitializer implements ApplicationContextInitializer {
    private static final String propertyName = "appContextInitializer-properties";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Map hashMap = new HashMap();
        MapPropertySource mapPropertySource = new MapPropertySource(propertyName, hashMap);
        hashMap.put("test.myKey", "appContextInitializer-properties");
        applicationContext.getEnvironment().getPropertySources().addFirst(mapPropertySource);
    }
}
