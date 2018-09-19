package com.mengcy.test;

import com.mengcy.cglib.CglibProxy;
import com.mengcy.dynamic.JDKProxy;
import com.mengcy.normal.IWorkerService;
import com.mengcy.normal.WorkerProxy;
import com.mengcy.normal.WorkerServiceImpl;

/**
 * Created by mengchaoyue on 2018/7/8.
 */
public class Test {

    public static void testStatic(){
        WorkerProxy proxy = new WorkerProxy(new WorkerServiceImpl());
        proxy.doSomething();
    }

    public static void testDynamic(){
        IWorkerService workerProxy = new JDKProxy<IWorkerService>().getProxy(new WorkerServiceImpl());
        workerProxy.doSomething();
        System.out.println(workerProxy.saySomething());
    }

    public static void testCglib(){
        IWorkerService cglibProxy = new CglibProxy().getProxy(WorkerServiceImpl.class);
        cglibProxy.doSomething();
        WorkerServiceImpl workerService = new CglibProxy().getProxy(WorkerServiceImpl.class);
        workerService.doSomething();
    }

    public static void main(String[] args){
        // 1.静态代理
        //testStatic();

        // 2.动态代理（基于反射）
        testDynamic();

        // 3.cglib代理（基于生成子类）
        //testCglib();
    }
}
