package com.fxz.monitor.server.chain;

import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.FilterChainManger;
import com.fxz.fuled.common.chain.FilterConfig;
import com.fxz.fuled.common.chain.Invoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fxz
 */
@Component
public class InvokerRunner implements ApplicationRunner {

    @Autowired
    FilterChainManger filterChainManger;
    @Autowired
    FilterConfig filterConfig;

    @Autowired
    TargetService targetService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Filter> testGroup = filterConfig.getFiltersByName("TEST");
        Invoker invoker = filterChainManger.buildInvokerChain(new Invoker<String>() {
            @Override
            public String invoke(String o) {
                System.out.println("target invoked...");
                return targetService.process(o);
            }
        }, testGroup);
        System.out.println("result->" + invoker.invoke("this is a testting..."));
    }
}
