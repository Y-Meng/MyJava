package com.mingtai.consumer.controller;

import com.mingtai.consumer.service.HelloService;
import com.mingtai.consumer.service.IFeignHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkzc-mcy create at 2018/4/25.
 */
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    IFeignHelloService feignHelloService;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return helloService.hello(name);
    }

    @RequestMapping("/hello2/{name}")
    public String hello2(@PathVariable String name){
        return feignHelloService.hello(name);
    }
}
