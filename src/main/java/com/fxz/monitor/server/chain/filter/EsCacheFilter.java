package com.fxz.monitor.server.chain.filter;


import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.Invoker;
import com.fxz.fuled.common.chain.annotation.FilterProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@FilterProperty(filterGroup = "Cache", name = EsCacheFilter.NAME, order = 1)
public class EsCacheFilter implements Filter<String, Object> {
    public static final String NAME = "EsCacheFilter";


    @Value("spring.cache.filter." + NAME + ".enabled:true")
    private boolean enabled;


    @Override
    public Object filter(String key, Invoker<String, Object> invoker) {
        if (enabled) {
            Object o = queryEs(key);
            if (Objects.nonNull(o)) {
                return o;
            }
        }
        return invoker.invoke(key);
    }

    /**
     * query es
     *
     * @param key
     * @return
     */
    private Object queryEs(String key) {
        return null;
    }
}
