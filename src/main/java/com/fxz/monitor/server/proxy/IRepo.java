package com.fxz.monitor.server.proxy;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author fxz
 */
public interface IRepo {

    @Cacheable(value = "user.id:")
    RepoPojo findById(String id);
}
