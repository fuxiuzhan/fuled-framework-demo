package com.fxz.monitor.server.newcache.objects;

import com.fxz.monitor.server.newcache.enums.CacheOpTypeEnum;
import com.fxz.monitor.server.newcache.annotation.BatchCache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CacheIn {
    private BatchCache batchCache;
    private ProceedingJoinPoint proceedingJoinPoint;

    private List<SingleOp> opList;

    private Map<String, Object> additions = new HashMap<>();

    public BatchCache getBatchCache() {
        return batchCache;
    }

    public void setBatchCache(BatchCache batchCache) {
        this.batchCache = batchCache;
    }

    public ProceedingJoinPoint getProceedingJoinPoint() {
        return proceedingJoinPoint;
    }

    public void setProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        this.proceedingJoinPoint = proceedingJoinPoint;
    }

    public List<SingleOp> getOpList() {
        return opList;
    }

    public void setOpList(List<SingleOp> opList) {
        this.opList = opList;
    }

    public Map<String, Object> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, Object> additions) {
        this.additions = additions;
    }

    public static class SingleOp {
        private CacheOpTypeEnum opTypeEnum;
        private String key;

        private boolean includeNull = Boolean.FALSE;

        private int expr = 10;

        private TimeUnit unit = TimeUnit.MINUTES;

        public CacheOpTypeEnum getOpTypeEnum() {
            return opTypeEnum;
        }

        public void setOpTypeEnum(CacheOpTypeEnum opTypeEnum) {
            this.opTypeEnum = opTypeEnum;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isIncludeNull() {
            return includeNull;
        }

        public void setIncludeNull(boolean includeNull) {
            this.includeNull = includeNull;
        }
    }
}
