package com.fxz.monitor.server;

import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fxz
 */

@EnableFuledBoot
public class MonitorServerApplication implements ApplicationRunner {


    @Autowired
    TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    public void run(ApplicationArguments args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("info->" + testService.getInfo());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
