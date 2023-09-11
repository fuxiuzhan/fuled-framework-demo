package com.fxz.monitor.server;

import com.alibaba.fastjson.JSON;
import com.fxz.fuled.common.dynamic.threadpool.pojo.ReporterDto;
import com.fxz.fuled.common.dynamic.threadpool.reporter.Reporter;
import com.fxz.fuled.dynamic.threadpool.ThreadPoolRegistry;
import com.fxz.fuled.service.annotation.EnableFuledBoot;
import com.fxz.monitor.server.dubbo.IProcessor;
import com.fxz.monitor.server.feign.DnsServerApi;
import com.fxz.monitor.server.feign.DnsServerApi2;
import com.fxz.monitor.server.orm.entity.UserInfo;
import com.fxz.monitor.server.orm.repository.UserRepository;
import com.fxz.monitor.server.proxy.IRepo;
import com.fxz.monitor.server.proxy.RepoPojo;
import com.fxz.monitor.server.service.TestProperty;
import com.fxz.monitor.server.service.TestService;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.TimeoutCountDown;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author fxz
 */

@EnableCaching
@EnableFuledBoot
@EnableFeignClients
@Slf4j
//@EnableDubbo
@MapperScan(basePackages = "com.fxz.monitor.server.orm")
@Import({TimedAspect.class})
public class MonitorServerApplication implements ApplicationRunner, ApplicationListener<ApplicationContextEvent>, ApplicationContextAware, Reporter {
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
    //    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    @Qualifier("dubboIRepo")
//    IRepo iRepo;
    @DubboReference(check = false, version = "1.0.0", tag = "A", retries = 1)
    IProcessor iProcessor;
    @Autowired
    private CollectorRegistry collectorRegistry;
    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private RedissonClient redissonClient;

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
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(1024));
        ThreadPoolRegistry.registerThreadPool("test", threadPoolExecutor);
//        for (int i = 0; i < 100; i++) {
//            Future<Object> submit = threadPoolExecutor.submit((Callable<Object>) () -> {
//                Thread.sleep(1000);
//                return "";
//            });
//            submit.get();
//            submit.cancel(Boolean.TRUE);
//            threadPoolExecutor.execute(() -> {
//                try {
//                    Thread.sleep(new Random().nextInt(10) * 1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//        ThreadPoolRegistry.registerThreadPool("test", (ThreadPoolExecutor) scheduledExecutorService);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("info->" + testService.getInfo());
            System.out.println("properties->" + testProperty.toString());
            System.out.println("testMyKey->" + testMyKey);
            try {
//                UserInfo userInfo = userRepository.selectById(1L);
//                userRepository.updateById(userInfo);
//                Future<String> name = threadPoolExecutor.submit(() -> iProcessor.process("name"));
//                System.out.println(name.get(10,TimeUnit.MILLISECONDS));
                RpcContext.getContext().set(CommonConstants.TIME_COUNTDOWN_KEY,
                        TimeoutCountDown.newCountDown(5, TimeUnit.SECONDS));
                RLock test1 = redissonClient.getLock("test");
                test1.tryLock(1, TimeUnit.SECONDS);
                Thread.sleep(1000);
                if (test1.isHeldByCurrentThread()) {
                    test1.unlock();
                }
                String test = iProcessor.process("test");
                System.out.println("repo->" + test);
                System.out.println("dns->" + dnsServerApi.query("www.fuled.xyz.", "A"));
//                System.out.println("dns->" + dnsServerApi2.query("www.fuled.xyz.", "A"));
//                applicationContext.publishEvent(new RefreshEvent(applicationContext, "", ""));
            } catch (Exception e) {
                System.out.println(e);
            }
//            loadBalancer.choose("dns-server");
//            loadBalancer.choose("monitor");
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        System.out.println("-------------------------------->" + event.getClass().getSimpleName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("----");
    }

    @Override
    public void report(List<ReporterDto> records) {
        System.out.println(JSON.toJSONString(records));
    }
}
