package com.mengcy.normal;

/**
 * Created by mengchaoyue on 2018/7/8.
 */
public class WorkerProxy implements IWorkerService{

    private IWorkerService workerService;

    public WorkerProxy(IWorkerService workerService){
        this.workerService = workerService;
    }

    @Override
    public void doSomething() {
        System.out.println("start work");
        workerService.doSomething();
        System.out.println("end work");
    }

    @Override
    public String saySomething() {
        return workerService.saySomething() + " from proxy";
    }
}
