package com.fxz.monitor.server;

import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.fuled.simple.cache.EnableSimpleCache;
import com.fxz.monitor.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

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
        testService.getInfo();
    }
}
