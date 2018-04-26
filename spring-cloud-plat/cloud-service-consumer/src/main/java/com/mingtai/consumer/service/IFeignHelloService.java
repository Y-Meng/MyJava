package com.mingtai.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zkzc-mcy create at 2018/4/26.
 * 使用Feign组件调用微服务，显式定义，模板化
 */
@FeignClient("service-demo")
public interface IFeignHelloService {

    @RequestMapping("/hello")
    String hello(@RequestParam("name") String name);
}
