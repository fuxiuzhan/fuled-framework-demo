package com.fxz.monitor.server.newcache.filters;

import com.fxz.fuled.common.chain.Invoker;
import com.fxz.monitor.server.newcache.filters.abs.AbsCacheFilter;
import com.fxz.monitor.server.newcache.objects.CacheIn;
import com.fxz.monitor.server.newcache.objects.CacheOut;

import static com.fxz.monitor.server.newcache.config.Constant.CACHE_GROUP_NAME;

public class ProceedCacheFilter extends AbsCacheFilter {
    private static final String NAME = "ProceedCacheFilter";

    public ProceedCacheFilter() {
        super(null);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String filterGroup() {
        return CACHE_GROUP_NAME;
    }

    @Override
    public CacheOut filter(CacheIn cacheIn, Invoker invoker) {
        CacheOut cacheOut = new CacheOut();
        try {
            Object proceed = cacheIn.getProceedingJoinPoint().proceed();
            cacheOut.setObject(proceed);
            return cacheOut;
        } catch (Throwable e) {
            cacheOut.setThrowable(e);
            cacheOut.setHasError(Boolean.TRUE);
        }
        return cacheOut;
    }
}
