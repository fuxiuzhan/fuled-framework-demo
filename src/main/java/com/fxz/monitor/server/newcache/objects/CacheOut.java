package com.fxz.monitor.server.newcache.objects;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CacheOut {
    private Object object;
    private long lastAccessTime;
    private long exprInSeconds;

    private boolean hasError = Boolean.FALSE;

    private Throwable throwable;

    private Map<String, Object> additions = new HashMap<>();

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getExprInSeconds() {
        return exprInSeconds;
    }

    public void setExprInSeconds(long exprInSeconds) {
        this.exprInSeconds = exprInSeconds;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map<String, Object> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, Object> additions) {
        this.additions = additions;
    }
}
