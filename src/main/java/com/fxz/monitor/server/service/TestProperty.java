package com.fxz.monitor.server.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author fxz
 */
@ConfigurationProperties("test")
@Component
@Data
public class TestProperty {

    private String name;

    private String addr;

    @Override
    public String toString() {
        return "TestProperty{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
