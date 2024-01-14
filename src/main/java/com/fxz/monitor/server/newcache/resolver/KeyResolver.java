package com.fxz.monitor.server.newcache.resolver;

import com.fxz.monitor.server.newcache.annotation.Cache;
import org.aspectj.lang.ProceedingJoinPoint;

public interface KeyResolver {

    String resolve(ProceedingJoinPoint proceedingJoinPoint, Cache cache);
}
