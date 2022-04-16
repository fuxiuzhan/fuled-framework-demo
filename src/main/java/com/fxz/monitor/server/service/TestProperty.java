package com.fxz.monitor.server.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author fxz
 */
@ConfigurationProperties("test")
@Component
@RefreshScope
public class TestProperty {

    private String name;

    private String addr;

    private String myKey;

    public void setMyKey(String myKey) {
        this.myKey = myKey;
    }

    public String getMyKey() {
        return myKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "TestProperty{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", myKey='" + myKey + '\'' +
                '}';
    }
}
