package com.mingtai.base.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  zkzc-mcy on 2017/10/18.
 * 基本任务管理，用于spring-task配置文件配置任务的信息管理；
 * 功能有限无法动态创建销毁任务，只能通过设置enable模拟任务停止启动
 */
public class TaskManager {

    public static boolean defaultEnable = false;

    /**
     * 任务队列
     */
    private static Map<String,TaskConfig> taskInfoMap = new HashMap<>();

    /**
     * 根据任务名称获取任务
     * @param className 实现类名称
     * @return
     */
    public static synchronized TaskConfig getTaskInfo(String taskName, String className) {
        TaskConfig taskConfig = taskInfoMap.get(className);
        if(taskConfig == null){
            taskConfig = new TaskConfig(taskName, className);
            if(defaultEnable){
                taskConfig.setTaskStatus(1);
            }else{
                taskConfig.setTaskStatus(0);
            }
            taskInfoMap.put(className, taskConfig);
        }
        return taskConfig;
    }

    /**
     * 添加任务信息
     * @param className 任务实现类名称
     * @param taskConfig 任务信息
     */
    public static void putTaskInfo(String className, TaskConfig taskConfig) {
        taskInfoMap.put(className, taskConfig);
    }

    /**
     * 获取任务信息列表
     * @return
     */
    public static List<TaskConfig> getAllTaskInfo(){
        List<TaskConfig> taskConfigList = new ArrayList<>();
        taskConfigList.addAll(taskInfoMap.values());
        return taskConfigList;
    }

    /**
     * 持久化更新某个任务信息
     * @param taskConfig 任务信息
     */
    public static void updateTaskInfo(TaskConfig taskConfig) {
        TaskConfig target = taskInfoMap.get(taskConfig.getTaskClass());
        if(target != null){
            target.setTaskStatus(taskConfig.getTaskStatus());
        }
    }

    public static void enableAll(){
        defaultEnable = true;
        for(TaskConfig config : taskInfoMap.values()){
            config.setTaskStatus(1);
        }
    }

    public static void disableAll(){
        defaultEnable = false;
        for(TaskConfig config : taskInfoMap.values()){
            config.setTaskStatus(0);
        }
    }

}
