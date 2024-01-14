package com.fxz.monitor.server.newcache.filters;

import com.fxz.fuled.common.chain.Invoker;
import com.fxz.monitor.server.newcache.container.CacheContainer;
import com.fxz.monitor.server.newcache.filters.abs.AbsCacheFilter;
import com.fxz.monitor.server.newcache.objects.CacheIn;
import com.fxz.monitor.server.newcache.objects.CacheOut;

import java.util.Arrays;

import static com.fxz.monitor.server.newcache.config.Constant.CACHE_GROUP_NAME;

public class LocalCacheFilter extends AbsCacheFilter {

    private static final String NAME = "LocalCacheFilter";

    public LocalCacheFilter(CacheContainer cacheContainer) {
        super(cacheContainer);
    }

    @Override
    public CacheOut filter(CacheIn cacheIn, Invoker<CacheIn, CacheOut> invoker) {
        //
        if (Arrays.stream(cacheIn.getBatchCache().caches()).filter(c -> c.localTurbo()).findFirst().isPresent()) {
            return super.filter(cacheIn, invoker);
        } else {
            return invoker.invoke(cacheIn);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE >> 2;
    }

    @Override
    public String filterGroup() {
        return CACHE_GROUP_NAME;
    }
}
