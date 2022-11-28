package com.fxz.monitor.server.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fxz
 */
public class SpringAppRunListener implements SpringApplicationRunListener {

    private static final String propertyName = "springAppRunListener-properties";

    /**
     * 固定构造方法，没有会报错
     *
     * @param application
     * @param args
     */
    public SpringAppRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        Map hashMap = new HashMap();
        MapPropertySource mapPropertySource = new MapPropertySource(propertyName, hashMap);
        hashMap.put("test.myKey", "springAppRunListener-properties");
        context.getEnvironment().getPropertySources().addFirst(mapPropertySource);
    }
}
