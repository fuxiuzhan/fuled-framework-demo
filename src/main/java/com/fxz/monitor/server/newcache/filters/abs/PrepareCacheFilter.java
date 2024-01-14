package com.fxz.monitor.server.newcache.filters.abs;

import com.fxz.fuled.common.chain.Invoker;
import com.fxz.monitor.server.newcache.annotation.BatchCache;
import com.fxz.monitor.server.newcache.annotation.Cache;
import com.fxz.monitor.server.newcache.expr.Evaluate;
import com.fxz.monitor.server.newcache.objects.CacheIn;
import com.fxz.monitor.server.newcache.objects.CacheOut;
import com.fxz.monitor.server.newcache.resolver.KeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * first
 */
@Slf4j
public class PrepareCacheFilter extends AbsCacheFilter {
    private KeyResolver keyResolver;

    public PrepareCacheFilter(KeyResolver keyResolver) {
        super(null);
        this.keyResolver = keyResolver;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public String filterGroup() {
        return null;
    }

    @Override
    public boolean enabled() {
        return Boolean.FALSE;
    }

    @Override
    public CacheOut filter(CacheIn cacheIn, Invoker<CacheIn, CacheOut> invoker) {
        //prepare key
        //push save & del list
        BatchCache batchCache = cacheIn.getBatchCache();
        Cache[] caches = batchCache.caches();
        List<CacheIn.SingleOp> opList = new ArrayList<>();
        for (Cache cache : caches) {
            if (evaluateCondition(cacheIn.getProceedingJoinPoint(), cache)) {
                CacheIn.SingleOp singleOp = new CacheIn.SingleOp();
                singleOp.setOpTypeEnum(cache.opType());
                singleOp.setIncludeNull(cache.includeNullResult());
                singleOp.setKey(keyResolver.resolve(cacheIn.getProceedingJoinPoint(), cache));
            }
        }
        cacheIn.setOpList(opList);
        return invoker.invoke(cacheIn);
    }

    private boolean evaluateCondition(ProceedingJoinPoint proceedingJoinPoint, Cache cache) {
        boolean result = Boolean.TRUE;
        if (Objects.nonNull(cache) && StringUtils.hasText(cache.condition())) {
            try {
                result = Evaluate.evaluate(proceedingJoinPoint, cache.condition(), Boolean.class);
                if (Objects.nonNull(result)) {
                    return result;
                }
            } catch (Exception e) {
                result = Boolean.FALSE;
                log.warn("condition evaluate error，method->{}, error->{}", proceedingJoinPoint.getSignature().getName(), e);
            }
        }
        return result;
    }
}
