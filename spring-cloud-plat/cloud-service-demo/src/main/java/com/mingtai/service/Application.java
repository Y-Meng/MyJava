package com.mingtai.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkzc-mcy create at 2018/4/25.
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@MapperScan("com.mingtai.service.*.mapper")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    /**描述：测试*/
    @RequestMapping("/hello")
    public String hello(String name){
        return "hello " + name;
    }
}
