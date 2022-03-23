package com.fxz.monitor.server.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * @author fxz
 */
@Configuration
public class RepoConfig {

    @Bean
    IRepo injectRepo() {
        IRepo o = (IRepo) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IRepo.class},
                (proxy, method, args) -> new RepoPojo("二狗", 24));
        return o;
    }

    @Bean
    IRepo2 injectRepo2() {
        IRepo2 o = (IRepo2) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IRepo2.class},
                (proxy, method, args) -> "repo2");
        return o;
    }
}
