package com.fxz.monitor.server.chain;


import com.fxz.fuled.common.chain.FilterChain;
import com.fxz.fuled.common.chain.FilterChainManger;
import com.fxz.fuled.common.chain.Invoker;
import com.fxz.fuled.service.annotation.EnableFuledBoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@EnableCaching
@EnableFuledBoot
@Import(FilterChainManger.class)
@Slf4j
public class TestMain implements ApplicationRunner {
    @Autowired
    private FilterChainManger filterChainManger;

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        /**
         *
         * 执行的查询顺序：LocalCacheFilter->EsCacheFilter->MysqlFilter->tailFilter [no data found at final]
         * 顺序order 控制，需在同一个filterGroup
         * 最好配合切面使用
         * 类似于 com.fxz.fuled.simple.cache.CacheAspect
         */

        Invoker invoker = filterChainManger.getInvoker("Cache", o -> {
            log.warn("no data found at final");
            return null;
        });
        String id = "id1";
        Object invoke = invoker.invoke(id);
        System.out.println("result->" + invoke);

    }
}
