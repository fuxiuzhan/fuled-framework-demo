package com.fxz.monitor.server.env;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fxz
 */
@Component
public class EnvAware implements EnvironmentAware {
    private static final String propertyName = "envAware-properties";

    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        Map hashMap = new HashMap();
        MapPropertySource mapPropertySource = new MapPropertySource(propertyName, hashMap);
        hashMap.put("test.myKey", "envAware-properties");
        configurableEnvironment.getPropertySources().addFirst(mapPropertySource);
    }
}
