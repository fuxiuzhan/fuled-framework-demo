package com.fxz.monitor.server.feign;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

/**
 * @author fxz
 */
public class CustRule extends RoundRobinRule {
    @Override
    public Server choose(Object key) {
        return super.choose(key);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
    }
}
