package com.mingtai.service.user;

import com.github.pagehelper.PageHelper;
import com.meng.model.Page;
import com.mingtai.service.user.entity.User;
import com.mingtai.service.user.entity.UserExample;
import com.mingtai.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zkzc-mcy create at 2018/6/25.
 */
@RestController
public class UserService implements IUserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public Page<User> users(Page page){
        UserExample userExample = new UserExample();
        page.setTotal(userMapper.countByExample(userExample));
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        page.setRows(userMapper.selectByExample(userExample));
        return page;
    }

}
