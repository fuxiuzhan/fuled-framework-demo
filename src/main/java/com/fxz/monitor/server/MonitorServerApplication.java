package com.fxz.monitor.server;

import com.fxz.fuled.dynamic.threadpool.ThreadPoolRegistry;
import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.dubbo.IProcessor;
import com.fxz.monitor.server.feign.DnsServerApi;
import com.fxz.monitor.server.feign.DnsServerApi2;
import com.fxz.monitor.server.orm.repository.UserRepository;
import com.fxz.monitor.server.proxy.IRepo;
import com.fxz.monitor.server.service.TestProperty;
import com.fxz.monitor.server.service.TestService;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ApplicationContextEvent;
import springfox.documentation.service.Tags;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author fxz
 */

@EnableCaching
@EnableFuledBoot
@EnableFeignClients
@Slf4j
@EnableDubbo
@MapperScan(basePackages = "com.fxz.monitor.server.orm")
@Import({TimedAspect.class})
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

    @DubboReference(check = false)
    IProcessor iProcessor;

    @Autowired
    private CollectorRegistry collectorRegistry;


    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public CountedAspect countedAspect(MeterRegistry meterRegistry) {
        return new CountedAspect(meterRegistry);
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        System.out.println("start");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                String poll = synchronousQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        synchronousQueue.put("test");
        System.out.println("end");
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(1024));
        ThreadPoolRegistry.registerThreadPool("test", threadPoolExecutor);
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(new Random().nextInt(10) * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("info->" + testService.getInfo());
            System.out.println("properties->" + testProperty.toString());
            System.out.println("testMyKey->" + testMyKey);
            try {
//                userRepository.findById(1L,1L);
                iProcessor.process("name");
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
