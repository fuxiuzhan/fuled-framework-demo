package com.fxz.monitor.server.distools;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface RateLimit {

    long rate();

    long rateInterval();

    TimeUnit unit();

    String limiterName();

    int expireSecond() default 60;

    String feedBack() default "";

    @Target({METHOD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        RateLimit[] value();
    }
}
