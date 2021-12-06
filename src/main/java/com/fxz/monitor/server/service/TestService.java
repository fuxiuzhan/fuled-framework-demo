package com.fxz.monitor.server.service;


import com.fxz.fuled.config.starter.annotation.DimaondConfigChangeListener;
import com.fxz.fuled.config.starter.model.ConfigChangeEvent;
import com.fxz.fuled.logger.starter.annotation.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author fxz
 */
@Service
@Slf4j
public class TestService {

    @Value("${test.value:abc}")
    private String value;

    //    @Cache
    @Monitor
    public String getInfo() {
        return value;
    }


    @DimaondConfigChangeListener(interestedKeys = "test.value", interestedKeyPrefixes = "test1")
    public void configListener(ConfigChangeEvent event) {
        event.changedKeys().forEach(k -> {
            log.info("config changes namespace->{},key->{},value->{}", event.getNamespace(), k, event.getChange(k));
        });
    }
}
