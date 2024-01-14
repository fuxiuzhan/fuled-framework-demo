package com.fxz.monitor.server.newcache.filters.abs;

import com.fxz.fuled.common.chain.Filter;
import com.fxz.monitor.server.newcache.objects.CacheIn;
import com.fxz.monitor.server.newcache.objects.CacheOut;

public abstract class PropertiesFilter implements Filter<CacheIn, CacheOut> {

    public String getName() {
        return "";
    }

    public int order() {
        return Integer.MAX_VALUE;
    }

    public String filterGroup() {
        return "";
    }

    public boolean enabled() {
        return Boolean.TRUE;
    }

}
