package com.fxz.monitor.server.chain.filter;

import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.Invoker;
import com.fxz.fuled.common.chain.annotation.FilterProperty;
import com.fxz.monitor.server.chain.pojo.CacheValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@FilterProperty(filterGroup = "Cache", name = LocalCacheFilter.NAME, order = 0)
public class LocalCacheFilter implements Filter<String, Object> {
    public static final String NAME = "LocalCacheFilter";

    @Value("${spring.cache.filter.local.size:1000}")
    private Integer cacheSize;

    @Value("spring.cache.filter." + NAME + ".enabled:true")
    private boolean enabled;

    @Value("spring.cache.filter.local.ttl:3600")
    private int ttl;


    private LRUCache<String, CacheValue> lruCache = new LRUCache(cacheSize);

    @Override
    public Object filter(String key, Invoker<String, Object> invoker) {
        if (enabled) {
            CacheValue cacheValue = lruCache.get(key);
            if (Objects.nonNull(cacheValue)) {
                if (System.currentTimeMillis() - cacheValue.getLastAccessTime() < cacheValue.getExprInSeconds() * 1000) {
                    return cacheValue.getObject();
                }
            }
            Object invoke = invoker.invoke(key);
            CacheValue result = new CacheValue();
            result.setObject(invoke);
            result.setExprInSeconds(ttl);
            result.setLastAccessTime(System.currentTimeMillis());
            lruCache.put(key, cacheValue);
            //memo 内存缓存，外部逻辑不能修改引用，否则会污染缓存
            return invoke;
        }
        return invoker.invoke(key);
    }
}
