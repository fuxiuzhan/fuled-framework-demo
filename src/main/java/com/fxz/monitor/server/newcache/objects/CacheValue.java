package com.fxz.monitor.server.newcache.objects;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheValue implements Serializable {
    private Object object;
    private long lastAccessTime;
    private long exprInSeconds;

    public static CacheValue build(CacheOut cacheOut) {
        CacheValue cache = new CacheValue();
        cache.setExprInSeconds(cacheOut.getExprInSeconds());
        cache.setObject(cacheOut.getObject());
        cache.setLastAccessTime(cacheOut.getLastAccessTime());
        return cache;
    }
}

