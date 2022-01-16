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
@FilterProperty(name = "testFilter1", filterGroup = TestFilter1.GROUP)
@Slf4j
public class TestFilter1 implements Filter<String> {
    public static final String GROUP = "TEST";

    @Override
    public String filter(String s, Invoker invoker) {
        log.info("TestFilter1 before s->{},invoker->{}", s, invoker);
        String invoke = (String) invoker.invoke(s);
        log.info("TestFilter1 s->{},invoker->{},result->{}", s, invoker, invoke);
        return invoke;
    }
}
