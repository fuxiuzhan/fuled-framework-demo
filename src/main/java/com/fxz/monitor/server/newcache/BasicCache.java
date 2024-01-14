package com.fxz.monitor.server.newcache;

import com.fxz.monitor.server.newcache.filters.LocalCacheFilter;

public enum BasicCache {
    LOCAL("local", LocalCacheFilter.class);;

    private String name;
    private Class clazz;

    BasicCache(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }
}
