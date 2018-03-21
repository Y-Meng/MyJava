package com.mcy.aop.impl;

import com.mcy.aop.IExtendService;
import org.springframework.stereotype.Component;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
@Component
public class ExtendServiceImpl implements IExtendService {

    @Override
    public void doExtendThing() {
        System.out.println("do extend thing");
    }
}
