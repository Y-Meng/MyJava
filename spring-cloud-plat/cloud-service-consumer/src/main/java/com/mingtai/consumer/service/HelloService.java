package com.mingtai.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zkzc-mcy create at 2018/4/25.
 * RestTemplate调用服务基本方式
 */
@Service
public class HelloService {


    @Autowired
    RestTemplate restTemplate;

    public String hello(String name){
        return restTemplate.getForObject("http://service-demo/hello?name="+name, String.class);
    }
}
