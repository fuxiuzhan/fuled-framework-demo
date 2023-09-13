package com.fxz.monitor.server.chain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheValue implements Serializable {
    private Object object;
    private long lastAccessTime;
    private long exprInSeconds;
}
