package com.fxz.monitor.server.chain;

import com.fxz.fuled.logger.starter.annotation.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fxz
 */
@Slf4j
@Service
public class TargetService {

    @Monitor
    public String process(String text) {
        log.info("TestService before invoke text->{}", text);
        String result = "TestService-" + text;
        log.info("TestService after invoke result->{}", result);
        return result;
    }
}
