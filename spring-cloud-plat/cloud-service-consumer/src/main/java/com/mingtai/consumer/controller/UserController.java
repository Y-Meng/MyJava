package com.mingtai.consumer.controller;

import com.meng.model.Page;
import com.mingtai.service.user.IUserService;
import com.mingtai.service.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkzc-mcy create at 2018/6/27.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/page")
    public Page<User> users(Page page){
        return userService.users(page);
    }
}
