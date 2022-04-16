package com.fxz.monitor.server.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author fxz
 */
@Component
public class EnvPostProcessor implements EnvironmentPostProcessor {
    private static final String propertyName = "envPostProcessor-properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(propertyName, properties);
        properties.put("test.myKey", "envPostProcessor-properties");
        environment.getPropertySources().addFirst(propertiesPropertySource);
    }
}
