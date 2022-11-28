package com.fxz.monitor.server.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fxz
 */
@Component
public class EnvPostProcessor implements EnvironmentPostProcessor {
    private static final String propertyName = "envPostProcessor-properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map hashMap = new HashMap();
        MapPropertySource mapPropertySource = new MapPropertySource(propertyName, hashMap);
        hashMap.put("test.myKey", "envPostProcessor-properties");
        environment.getPropertySources().addFirst(mapPropertySource);
    }
}
