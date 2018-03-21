package com.mcy.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
@Aspect
public class TargetAspect implements Ordered{
    /**
     * 为IExtendService的对象添加切点
     */
    @Pointcut("target(com.mcy.aop.IExtendService)")
    public void after(){}

    @AfterReturning("after()")
    public void afterExecute(){
        System.out.println("-- target aop after execute--");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
