package com.mingtai.base.task;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author  zkzc-mcy on 2017/10/18.
 */
public class TaskConfig {

    protected Integer id;

    protected String taskName;

    protected String taskClass;

    protected String taskTrigger;

    protected Integer taskStatus = 1;

    protected Long executeTimes = 0L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date lastExecuteTime ;

    protected Integer failTimes = 0;

    protected String lastFailInfo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date lastFailTime;

    public TaskConfig(){}

    public TaskConfig(String name, String className){
        this.taskClass = className;
        this.taskName = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTrigger() {
        return taskTrigger;
    }

    public void setTaskTrigger(String taskTrigger) {
        this.taskTrigger = taskTrigger;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(Long executeTimes) {
        this.executeTimes = executeTimes;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Integer getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    public String getLastFailInfo() {
        return lastFailInfo;
    }

    public void setLastFailInfo(String lastFailInfo) {
        this.lastFailInfo = lastFailInfo;
    }

    public Date getLastFailTime() {
        return lastFailTime;
    }

    public void setLastFailTime(Date lastFailTime) {
        this.lastFailTime = lastFailTime;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getKey(){
        if(this.id != null){
            return this.taskClass + "-" + this.id;
        }else{
            return this.taskClass + "-" + this.taskName;
        }
    }
}
