package com.fxz.monitor.server.chain;

import com.fxz.fuled.common.chain.Filter;
import com.fxz.fuled.common.chain.Invoker;

/**
 * @author fxz
 */
public class TestFilter3 implements Filter<String> {
    @Override
    public Void filter(String s, Invoker invoker) {

        return null;
    }
}
