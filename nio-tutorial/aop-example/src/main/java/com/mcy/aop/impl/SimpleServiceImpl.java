package com.mcy.aop.impl;

import com.mcy.aop.ISimpleService;
import org.springframework.stereotype.Component;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
@Component
public class SimpleServiceImpl implements ISimpleService {

    /**
     * 实现接口方法
     */
    @Override
    public void doSomething() {
        System.out.println("do something");
    }

    /**
     * 实现类自定义方法
     */
    public void doAnotherThing(){
        System.out.println("do another thing");
    }
}
