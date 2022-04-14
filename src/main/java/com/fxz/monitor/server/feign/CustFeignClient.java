package com.fxz.monitor.server.feign;

import feign.Client;
import feign.Request;
import feign.Response;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author fxz
 */
public class CustFeignClient implements Client {
    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        return Response.builder().status(200).request(request).body("test", Charset.defaultCharset()).build();
    }
}
