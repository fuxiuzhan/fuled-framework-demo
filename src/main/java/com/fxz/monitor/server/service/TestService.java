package com.fxz.monitor.server.service;


import com.fxz.fuled.simple.cache.Cache;
import org.springframework.stereotype.Service;

/**
 * @author fxz
 */
@Service
public class TestService {


    @Cache
    public String getInfo() {
        return "111";
    }
}
