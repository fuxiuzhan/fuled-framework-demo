package com.fxz.monitor.server.feign;

import com.fxz.fuled.logger.starter.annotation.Monitor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fxz
 */
@FeignClient("dns-servers")
public interface DnsServerApi2 {

    @Monitor
    @PostMapping("/storage/query")
    String query(@RequestParam("host") String host, @RequestParam("type") String type);
}
