package com.fxz.monitor.server.newcache.filters.abs;

import com.fxz.fuled.common.chain.Invoker;
import com.fxz.monitor.server.newcache.container.CacheContainer;
import com.fxz.monitor.server.newcache.enums.CacheOpTypeEnum;
import com.fxz.monitor.server.newcache.objects.CacheIn;
import com.fxz.monitor.server.newcache.objects.CacheOut;
import com.fxz.monitor.server.newcache.objects.CacheValue;

import java.util.Objects;

public abstract class AbsCacheFilter extends PropertiesFilter {
    private CacheContainer cacheContainer;

    public AbsCacheFilter(CacheContainer cacheContainer) {
        this.cacheContainer = cacheContainer;
    }

    @Override
    public CacheOut filter(CacheIn cacheIn, Invoker<CacheIn, CacheOut> invoker) {
        //add
        CacheOut invoke = null;
        try {
            for (CacheIn.SingleOp singleOp : cacheIn.getOpList()) {
                if (CacheOpTypeEnum.SAVE.equals(singleOp.getOpTypeEnum())) {
                    CacheValue cacheValue = cacheContainer.get(singleOp.getKey());
                    if (Objects.nonNull(cacheValue)) {
                        CacheOut cacheOut = new CacheOut();
                        cacheOut.setObject(cacheValue.getObject());
                        cacheOut.setLastAccessTime(cacheValue.getLastAccessTime());
                        cacheOut.setExprInSeconds(cacheValue.getExprInSeconds());
                        return cacheOut;
                    }
                }
            }
            invoke = invoker.invoke(cacheIn);
            return invoke;
        } finally {
            for (CacheIn.SingleOp singleOp : cacheIn.getOpList()) {
                if (CacheOpTypeEnum.DELETE.equals(singleOp.getOpTypeEnum())) {
                    cacheContainer.del(singleOp.getKey());
                }
                if (CacheOpTypeEnum.UPDATE.equals(singleOp.getOpTypeEnum())) {
                    if (Objects.nonNull(invoke) || singleOp.isIncludeNull()) {
                        cacheContainer.set(singleOp.getKey(), CacheValue.build(invoke));
                    }
                }
            }
        }
    }
}
