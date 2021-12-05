package com.fxz.monitor.server.service;


import com.fxz.fuled.logger.starter.annotation.Monitor;
import com.fxz.fuled.simple.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author fxz
 */
@Service
public class TestService {

    @Value("${test.value:abc}")
    private String value;

//    @Cache
    @Monitor
    public String getInfo() {
        return value;
    }
}
