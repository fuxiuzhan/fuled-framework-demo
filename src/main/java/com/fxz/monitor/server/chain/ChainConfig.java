package com.fxz.monitor.server.chain;

import com.fxz.fuled.common.chain.FilterChainManger;
import com.fxz.fuled.common.chain.FilterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author fxz
 */
@Component
public class ChainConfig {

    @Bean
    public FilterChainManger filterChainManger() {
        return new FilterChainManger();
    }

    @Bean
    public FilterConfig filterConfig() {
        return new FilterConfig();
    }
}
