package com.fxz.monitor.server.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

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
        Properties properties = new Properties();
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(propertyName, properties);
        properties.put("test.myKey", "springAppRunListener-properties");
        context.getEnvironment().getPropertySources().addFirst(propertiesPropertySource);
    }
}
