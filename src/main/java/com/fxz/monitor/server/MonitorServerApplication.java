package com.fxz.monitor.server;

import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.service.TestProperty;
import com.fxz.monitor.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fxz
 */

@EnableFuledBoot
public class MonitorServerApplication implements ApplicationRunner, EnvironmentAware {


    @Autowired
    TestService testService;
    @Autowired
   TestProperty testProperty;

    Environment context;

    public static void main(String[] args) {
//        System.getProperties().put("spring.application.name","monitor");
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("info->" + testService.getInfo());
                System.out.println("ptoperty->" + testProperty.toString());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.context=environment;
    }
}
