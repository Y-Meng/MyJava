package com.mcy.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
@Aspect
public class ThisAspect implements Ordered{

    /**
     * 为任何运行期为IExtendService的对象添加切点
     */
    @Pointcut("this(com.mcy.aop.IExtendService)")
    public void after(){}

    @AfterReturning("after()")
    public void afterExecute(){
        System.out.println("--this aop after execute--");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
