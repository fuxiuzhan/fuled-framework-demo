package com.fxz.monitor.server;

import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.feign.DnsServerApi;
import com.fxz.monitor.server.feign.DnsServerApi2;
import com.fxz.monitor.server.orm.repository.UserRepository;
import com.fxz.monitor.server.proxy.IRepo;
import com.fxz.monitor.server.service.TestProperty;
import com.fxz.monitor.server.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fxz
 */

@EnableCaching
@EnableFuledBoot
@EnableFeignClients
@Slf4j
@EnableDubbo
@MapperScan(basePackages = "com.fxz.monitor.server.orm")
public class MonitorServerApplication implements ApplicationRunner, ApplicationListener<ApplicationContextEvent>, ApplicationContextAware {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    TestService testService;
    @Autowired
    TestProperty testProperty;
    @Autowired
    LoadBalancerClient loadBalancer;
    @Autowired
    DnsServerApi dnsServerApi;
    @Autowired
    DnsServerApi2 dnsServerApi2;

    @Value("${test.myKey:default}")
    private String testMyKey;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("dubboIRepo")
    IRepo iRepo;

    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println("info->" + testService.getInfo());
            System.out.println("properties->" + testProperty.toString());
            System.out.println("testMyKey->" + testMyKey);
            try {
                userRepository.selectById(1);
                System.out.println("repo->" + iRepo.findById("test"));
                System.out.println("dns->" + dnsServerApi.query("www.fuled.xyz.", "A"));
//                System.out.println("dns->" + dnsServerApi2.query("www.fuled.xyz.", "A"));
//                applicationContext.publishEvent(new RefreshEvent(applicationContext, "", ""));
            } catch (Exception e) {
                System.out.println(e);
            }
//            loadBalancer.choose("dns-server");
//            loadBalancer.choose("monitor");
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        System.out.println("-------------------------------->" + event.getClass().getSimpleName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("----");
    }
}
