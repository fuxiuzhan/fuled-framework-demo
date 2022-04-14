package com.fxz.monitor.server.controller;


import com.fxz.monitor.server.proxy.IRepo;
import com.fxz.monitor.server.proxy.IRepo2;
import com.fxz.monitor.server.proxy.RepoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuled
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    IRepo repo;

    @Autowired
    IRepo2 repo2;

    @GetMapping("/test")
    @Caching(cacheable = {@Cacheable(value = "s", key = "#id", cacheManager = "cacheMgr")
            , @Cacheable(value = "m", key = "#id", cacheManager = "cacheMgr")
            , @Cacheable(value = "test.cache")}
            , evict = {@CacheEvict(value = "s", key = "#id")})
    public String testCache(String id) {
        return System.currentTimeMillis() + "";
    }

    @GetMapping("/repo")
    public RepoPojo getRepo() {
        return repo.findById("test");
    }

    @GetMapping("/repo2")
    public String getRepo2() {
        return repo2.findById("test");
    }

    @GetMapping("/del")
    @CacheEvict(value = "s", key = "#id",allEntries = true)
    public String del(String id) {
        return "del";
    }
}
