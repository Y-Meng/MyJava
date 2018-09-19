package com.mengcy.normal;

/**
 * Created by mengchaoyue on 2018/7/8.
 */
public class WorkerServiceImpl implements IWorkerService{

    @Override
    public void doSomething() {
        System.out.println("work hard");
    }

    @Override
    public String saySomething() {
        return "hello";
    }
}
