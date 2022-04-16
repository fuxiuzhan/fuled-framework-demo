package com.fxz.monitor.server.service;


import com.fxz.fuled.config.starter.annotation.DimaondConfigChangeListener;
import com.fxz.fuled.config.starter.model.ConfigChangeEvent;
import com.fxz.fuled.logger.starter.annotation.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * @author fxz
 */
@Service
@Slf4j
public class TestService implements EnvironmentAware {

    @Value("${test.value:abc}")
    private String value;

    private String inner;

    private Environment environment;

    @Autowired
    ConfigurableApplicationContext context;

    public TestService(@Value("${test.value:inner}") String inner) {
        this.inner = inner;
    }

    //    @Cache
    @Monitor
    public String getInfo() {
        return value + ":" + inner;
    }


    @DimaondConfigChangeListener(interestedKeys = "test.value", interestedKeyPrefixes = "test1")
    public void configListener(ConfigChangeEvent event) {
        event.changedKeys().forEach(k -> {
            log.info("config changes namespace->{},key->{},value->{}", event.getNamespace(), k, event.getChange(k));
            //触发refreshEvent
            context.publishEvent(new RefreshEvent(this, k, "pub"));
        });
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    @RefreshScope
    public PropertiesPropertySource testBean() {
        return new PropertiesPropertySource("11111", new Properties());
    }
}
