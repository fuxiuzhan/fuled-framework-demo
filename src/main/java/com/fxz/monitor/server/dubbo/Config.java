package com.fxz.monitor.server.dubbo;

import com.alibaba.fastjson.JSON;
import com.fxz.fuled.common.utils.ConfigUtil;
import com.fxz.monitor.server.proxy.IRepo;
import com.fxz.monitor.server.proxy.RepoImp;
import com.fxz.monitor.server.proxy.RepoPojo;
import org.apache.dubbo.config.*;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;


@Component("dubbo-config")
public class Config {

    @PostConstruct
    public void export() {
        //1.设置ServiceConfig实例
        ServiceConfig<IRepo> serviceServiceConfig = new ServiceConfig<>();
        //2.设置应用程序配置
        serviceServiceConfig.setApplication(new ApplicationConfig("test-provider"));
        //3.设置注册中心
        RegistryConfig registryConfig = new RegistryConfig("nacos://" + ConfigUtil.getEnv().getConfigServer() + ":" + ConfigUtil.getEnv().getPort() + "?namespace=" + ConfigUtil.getEnv().name());
        serviceServiceConfig.setRegistry(registryConfig);
        //4.设置接口以及实现类
        serviceServiceConfig.setInterface(IRepo.class);
        serviceServiceConfig.setRef(new RepoImp());
        serviceServiceConfig.setProtocol(new ProtocolConfig("dubbo", -1));
//        serviceServiceConfig.getProvider().setPort((int)(20880+Math.random()*10));
        //5.设置分组与版本
        serviceServiceConfig.setVersion("1.0.0");
        serviceServiceConfig.setTimeout(500000);
        serviceServiceConfig.setLoadbalance("random");
        //6.设置线程池策略
        //HashMap<String, String> parameters = new HashMap<>();
        //parameters.put("threadpool","mythreadpool");
        //serviceServiceConfig.setParameters(parameters);
//        AsyncContext
        //7.导出
        serviceServiceConfig.export();
    }

    @Bean("dubboIRepo")
    public IRepo init() {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("test-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("nacos://" + ConfigUtil.getEnv().getConfigServer() + ":" + ConfigUtil.getEnv().getPort() + "?namespace=" + ConfigUtil.getEnv().name());
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface("com.fxz.monitor.server.proxy.IRepo");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric(true);
        referenceConfig.setTimeout(1000);
        referenceConfig.setCheck(false);
        GenericService genericService = referenceConfig.get();
        return id -> {
            Map<String, Object> map = (Map<String, Object>) genericService.$invoke("findById", new String[]{String.class.getName()}, new Object[]{"test"});
            RepoPojo repoPojo = new RepoPojo();
            repoPojo.setName(JSON.toJSONString(map));
            return repoPojo;
        };
    }
}
