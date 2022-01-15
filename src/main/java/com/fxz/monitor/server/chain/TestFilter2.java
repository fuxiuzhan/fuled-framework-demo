package com.fxz.monitor.server.chain;

import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.Invoker;
import com.fxz.fuled.common.common.FilterProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author fxz
 */
@Component
@FilterProperty(name = "testFilter2", filterGroup = "TEST", order = 1)
@Slf4j
public class TestFilter2 implements Filter<String> {
    @Override
    public String filter(String s, Invoker invoker) {
        log.info("TestFilter2 before s->{},invoker->{}", s, invoker);
        String invoke = (String) invoker.invoke(s);
        log.info("TestFilter2 s->{},invoker->{},result->{}", s, invoker, invoke);
        return invoke;
    }
}
