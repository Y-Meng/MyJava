package com.mcy.aop;

import com.mcy.aop.impl.SimpleServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
public class ThisAopTest {

    private ApplicationContext appContext;

    @Test
    public void test(){
        appContext = new ClassPathXmlApplicationContext("classpath:aop-context.xml");

        SimpleServiceImpl simpleService = (SimpleServiceImpl) appContext.getBean("simpleServiceImpl");

        // 匹配到 this()
        simpleService.doSomething();

        // 匹配到 this()
        simpleService.doAnotherThing();

        // 匹配到 this() 和 target()
        ((IExtendService)simpleService).doExtendThing();
    }
}
