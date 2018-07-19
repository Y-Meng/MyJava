package com.mingtai.service.user;

import com.meng.model.Page;
import com.mingtai.service.user.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zkzc-mcy create at 2018/6/27.
 */

@FeignClient("service-demo")
@RequestMapping("/user")
public interface IUserService {

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    Page<User> users(@RequestBody Page page);
}
