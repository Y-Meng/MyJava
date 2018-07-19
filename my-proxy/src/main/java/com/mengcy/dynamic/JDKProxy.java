package com.mengcy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mengchaoyue on 2018/7/8.
 */
public class JDKProxy<T> implements InvocationHandler{

    private Object target;

    public T getProxy(Object t){
        this.target = t;
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("dynamic proxy start");
        method.invoke(target, args);
        System.out.println("dynamic proxy end");
        return null;
    }
}
