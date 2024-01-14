package com.fxz.monitor.server.newcache.container;

import com.fxz.monitor.server.newcache.annotation.Cache;
import com.fxz.monitor.server.newcache.objects.CacheValue;

import java.util.concurrent.TimeUnit;

public interface CacheContainer {

    CacheValue get(String key);

    CacheValue set(String key, CacheValue cacheValue);

    void del(String key);

    void clear();
}
