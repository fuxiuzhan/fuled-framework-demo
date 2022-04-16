package com.fxz.monitor.server.env;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author fxz
 */
@Component
public class EnvAware implements EnvironmentAware {
    private static final String propertyName = "envAware-properties";

    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        Properties properties = new Properties();
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(propertyName, properties);
        properties.put("test.myKey", "envAware-properties");
        configurableEnvironment.getPropertySources().addFirst(propertiesPropertySource);
    }
}
