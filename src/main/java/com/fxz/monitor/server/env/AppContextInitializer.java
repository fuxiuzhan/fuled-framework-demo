package com.fxz.monitor.server.env;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author fxz
 */
public class AppContextInitializer implements ApplicationContextInitializer {
    private static final String propertyName = "appContextInitializer-properties";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Properties properties = new Properties();
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(propertyName, properties);
        properties.put("test.myKey", "appContextInitializer-properties");
        applicationContext.getEnvironment().getPropertySources().addFirst(propertiesPropertySource);
    }
}
