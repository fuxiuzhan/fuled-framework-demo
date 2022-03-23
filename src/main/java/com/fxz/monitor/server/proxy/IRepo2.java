package com.fxz.monitor.server.proxy;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author fxz
 */
public interface IRepo2 {

    @Cacheable(value = "user.id:")
    String findById(String input);
}
