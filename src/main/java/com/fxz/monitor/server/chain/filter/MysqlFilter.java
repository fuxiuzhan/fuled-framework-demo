package com.fxz.monitor.server.chain.filter;


import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.Invoker;
import com.fxz.fuled.common.chain.annotation.FilterProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FilterProperty(filterGroup = "Cache", name = MysqlFilter.NAME, order = 2)
public class MysqlFilter implements Filter<String, Object> {

    public static final String NAME = "MysqlFilter";


    @Value("spring.cache.filter." + NAME + ".enabled:true")
    private boolean enabled;

    @Override
    public Object filter(String key, Invoker<String, Object> invoker) {
        if (enabled) {
            return queryDb(key);
        }
        return invoker.invoke(key);
    }

    /**
     * @param id
     * @return
     */
    private Object queryDb(String id) {
        return null;
    }
}
