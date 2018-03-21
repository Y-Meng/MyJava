package com.mingtai.base.task;

import java.util.Date;

/**
 * @author  zkzc-mcy on 2017/10/18.
 */
public abstract class BaseTask implements Runnable{

    protected TaskConfig taskConfig;

    protected String taskName = "未命名任务";

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 执行任务前
     */
    protected void preExecute(){
        if(taskConfig != null){
            taskConfig.setLastExecuteTime(new Date());
        }
    }

    /**
     *执行任务
     */
    protected abstract void execute();

    /**
     * 执行任务后
     */
    protected void afterExecute(){
        if(taskConfig != null){
            taskConfig.setExecuteTimes(taskConfig.getExecuteTimes() + 1);
            saveTaskConfig(taskConfig);
        }
    }

    /**
     * 执行任务，作为切面方法
     */
    public void executeTask(){
        if(taskConfig == null){
            taskConfig = TaskManager.getTaskInfo(taskName, getClass().getName());
        }

        if(taskConfig.getTaskStatus() > 0){
            try {
                preExecute();
                execute();
                afterExecute();
            }catch (Exception e){
                taskConfig.setFailTimes(taskConfig.getFailTimes() + 1);
                taskConfig.setLastFailTime(new Date());
                taskConfig.setLastFailInfo(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        if(taskConfig == null){
            execute();
        }else{
            executeTask();
        }
    }

    protected void saveTaskConfig(TaskConfig config){}

    public TaskConfig getTaskConfig() {
        return taskConfig;
    }

    public void setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }
}
