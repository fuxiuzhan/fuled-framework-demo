package com.fxz.monitor.server;

import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.service.TestProperty;
import com.fxz.monitor.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fxz
 */

@EnableCaching
@EnableFuledBoot
@EnableFeignClients
@EnableConfigurationProperties(TestProperty.class)
public class MonitorServerApplication implements ApplicationRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TestService testService;
    @Autowired
    TestProperty testProperty;

    @Value("${test.myKey:default}")
    private String testMyKey;

    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println("info->" + testService.getInfo());
            System.out.println("properties->" + testProperty.toString());
            System.out.println("testMyKey->" + testMyKey);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
