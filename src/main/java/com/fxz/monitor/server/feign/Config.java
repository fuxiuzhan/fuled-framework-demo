package com.fxz.monitor.server.feign;

import com.fxz.component.fuled.cat.starter.component.feign.CatFeignInterceptor;
import com.netflix.loadbalancer.IRule;
import feign.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author fxz
 */
@Component
@ConditionalOnClass(Client.class)
public class Config {

    //@Bean
    public CustFeignClient buildClient() {
        return new CustFeignClient();
    }


    @Bean
    public IRule buildIRule() {
        return new CustRule();
    }

    @Bean
    @ConditionalOnClass(CatFeignInterceptor.class)
    public CatFeignInterceptor catFeignInterceptor() {
        CatFeignInterceptor catFeignInterceptor = new CatFeignInterceptor();
        return catFeignInterceptor;
    }
}
