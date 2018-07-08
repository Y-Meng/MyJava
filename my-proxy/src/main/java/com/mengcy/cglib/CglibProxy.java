package com.mengcy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by mengchaoyue on 2018/7/8.
 */
public class CglibProxy implements MethodInterceptor{

    public <T> T getProxy(Class<T> clz){

        // 通过生成子类的方式代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallback(this);

        T proxy = (T) enhancer.create();
        return proxy;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.print("cglib proxy start");
        Object proxy = methodProxy.invokeSuper(o, objects);
        System.out.print("cglib proxy end");
        return proxy;
    }
}
