package com.fxz.monitor.server.dubbo.impl;

import com.fxz.monitor.server.dubbo.IProcessor;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0", tag = "A")
public class ProcessorA implements IProcessor {
    @Override
    public String process(String name) {
        return name + "A";
    }
}
