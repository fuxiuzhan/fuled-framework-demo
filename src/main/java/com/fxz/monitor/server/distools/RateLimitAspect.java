package com.fxz.monitor.server.distools;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class RateLimitAspect {

    @Autowired
    private Redisson redisson;

    @Pointcut("@annotation(com.xx.yy.annotation.RateLimit.List)")
    public void rateLimitList() {
    }

    @Pointcut("@annotation(com.xx.yy.annotation.RateLimit)")
    public void rateLimit() {
    }

    @Before("rateLimit()")
    public void acquire(JoinPoint jp) {
        log.info("RateLimitAspect acquire");
        acquire0(jp);
        log.info("RateLimitAspect acquire exit");
    }


    @Before("rateLimitList()")
    public void acquireList(JoinPoint jp) throws Throwable {
        log.info("RateLimitAspect acquireList");
        acquire0(jp);
        log.info("RateLimitAspect acquireList exit");
    }

    private void acquire0(JoinPoint jp) {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object[] args = jp.getArgs();
        String[] parameters = methodSignature.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<>();
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                parameterMap.put(parameters[i], args[i]);
            }
        }
        log.info("acquire0 method:{} args:{} parameters:{}", targetMethod.getName(), args, Arrays.toString(parameters));
        RateLimit[] rateLimits = null;
        if (targetMethod.isAnnotationPresent(RateLimit.List.class)) {
            RateLimit.List list = targetMethod.getAnnotation(RateLimit.List.class);
            rateLimits = list.value();
        } else if (targetMethod.isAnnotationPresent(RateLimit.class)) {
            RateLimit limit = targetMethod.getAnnotation(RateLimit.class);
            rateLimits = new RateLimit[]{limit};
        }
        if (rateLimits == null) {
            log.warn("not config rateLimiter");
            return;
        }
        for (RateLimit limit : rateLimits) {
            String name = limit.limiterName();
            if (StringUtils.isBlank(name)) {
                log.error("rateLimiter name is blank,skip");
                continue;
            }
            String realName = name;
            //参数替换可优化
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                String pattern = "#{" + entry.getKey() + "}";
                if (name.contains(pattern)) {
                    Object val = entry.getValue();
                    if (val == null) {
                        val = "null";
                    }
                    realName = realName.replace(pattern, val.toString());
                }
            }
            log.info("configName:{} realName:{}", name, realName);
            RRateLimiter rateLimiter = redisson.getRateLimiter(realName);
            if (!rateLimiter.isExists()) {
                rateLimiter.trySetRate(RateType.OVERALL, limit.rate(), limit.rateInterval(), RateIntervalUnit.SECONDS);
            }
            if (!rateLimiter.tryAcquire()) {
                log.warn("rateLimiter:{} acquire fail", realName);
                throw new RuntimeException("调用频率超限制，请稍后再试");
            }
        }
    }
}

