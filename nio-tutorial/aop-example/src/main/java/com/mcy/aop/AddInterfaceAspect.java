package com.mcy.aop;

import com.mcy.aop.impl.ExtendServiceImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.core.Ordered;

/**
 * @author zkzc-mcy create at 2018/3/21.
 */
@Aspect
public class AddInterfaceAspect implements Ordered{

    /**
     * 为SimpleServiceImpl类增加接口实现
     * defaultImpl为增加接口的默认实现类
     */
    @DeclareParents(value = "com.mcy.aop.impl.SimpleServiceImpl", defaultImpl = ExtendServiceImpl.class)
    public IExtendService extendService;

    @Override
    public int getOrder() {
        // 实现排序接口，设置排序2
        return 2;
    }
}
