package com.fxz.monitor.server.newcache.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author fxz
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface BatchCache {

    Cache[] caches() default {};
}
