package com.mingtai.base.task;

import com.mingtai.base.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author zkzc-mcy create at 2017/12/5.
 * 定时任务动态管理工具，可动态创建销毁更新定时任务；基于spring-task
 */
public class TaskScheduler {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskScheduler.class);
    private static final Map<String, TaskConfig> TASKS = new HashMap<>(64);
    private static final Map<String, ScheduledFuture<?>> SCHEDULED_FUTURE = new HashMap<>(64);

    private static final int CORE_POOL_SIZE = 16;

    private static final ConcurrentTaskScheduler ct = new ConcurrentTaskScheduler(new ScheduledThreadPoolExecutor(CORE_POOL_SIZE));

    /**
     * 启动一个计划任务.
     * @param taskConfig 当前进行的任务.
     */
    public static void start(TaskConfig taskConfig) throws TaskException {

        try {
            if (StringUtils.isEmpty(taskConfig.getTaskClass())) {
                throw new TaskException("任务执行类不能为空.");
            }

            if (StringUtils.isEmpty(taskConfig.getTaskTrigger())) {
                throw new TaskException("任务的调度表达式不能为空.");
            }

//            Class clazz = Class.forName(taskConfig.getTaskClass());
//            if(clazz == null){
//                throw new TaskException("任务实现类加载失败");
//            }
//
//            Object taskInstance = clazz.newInstance();

            Object taskInstance = AppContext.getBean(taskConfig.getTaskClass());

            if(taskInstance != null && taskInstance instanceof Runnable){

                if(taskInstance instanceof BaseTask){
                    ((BaseTask) taskInstance).setTaskConfig(taskConfig);
                }

                ScheduledFuture<?> scheduledFuture = ct.schedule((Runnable) taskInstance, new CronTrigger(taskConfig.getTaskTrigger()));
                SCHEDULED_FUTURE.put(taskConfig.getKey(), scheduledFuture);
                TASKS.put(taskConfig.getKey(), taskConfig);
                LOGGER.info("任务" + taskConfig.getKey() + "已启动.");
            }else{
                throw new TaskException("任务实现类未实现可执行接口");
            }
        } catch (Exception e) {
            LOGGER.info(null, e);
            throw new TaskException(e);
        }
    }

    /**
     * 停止一个计划任务.
     * @param taskKey 任务编号，类名.
     */
    public static void stop(String taskKey) throws TaskException{

        LOGGER.info("正在停止任务 " + taskKey);
        if (StringUtils.isEmpty(taskKey)) {
            throw new TaskException("指定任务执行不能为空.");
        }

        try {
            ScheduledFuture<?> scheduledFuture = SCHEDULED_FUTURE.remove(taskKey);
            if (scheduledFuture == null) {
                throw new TaskException("任务key:" + taskKey + "不存在.");
            } else {
                if (!scheduledFuture.isCancelled()) {
                    /** false 表示当前任务若正在执行，则待其执行结束，再结束此任务. */
                    scheduledFuture.cancel(false);
                }
            }
        } catch (Exception e) {
            LOGGER.info(null, e);
            throw new TaskException(e);
        }
    }

    /**
     * 重启任务
     * @param config
     */
    public static void restart(TaskConfig config) throws TaskException{

        LOGGER.info("正在重启任务 " + config.getKey() + "执行频率.");
        if (StringUtils.isEmpty(config.getKey())) {
            throw new TaskException("任务KEY不能为空.");
        }

        if (StringUtils.isEmpty(config.getTaskTrigger())) {
            throw new TaskException("任务的调度表达式不能为空.");
        }

        TaskConfig task = TASKS.get(config.getKey());
        if (task != null) {

            /** 1 停止任务. */
            ScheduledFuture<?> scheduledFuture = SCHEDULED_FUTURE.remove(config.getKey());
            scheduledFuture.cancel(false);

            /** 2 重启任务. */
            start(task);
        }else {
            start(config);
        }
    }

    /**
     * 重新设置当前任务的执行频率.
     *
     * @param taskKey 任务编号.
     */
    public static void resetTrigger(String taskKey, String cronExpression) throws TaskException{

        LOGGER.info("正在修改当前任务 " + taskKey + "执行频率.");
        if (StringUtils.isEmpty(taskKey)) {
            throw new TaskException("the taskid must be not empty.");
        }

        if (StringUtils.isEmpty(cronExpression)) {
            throw new TaskException("任务的调度表达式不能为空.");
        }

        TaskConfig task = TASKS.get(taskKey);
        if (task != null) {
            if (cronExpression.equals(task.getTaskTrigger())) {
                return;
            }

            /** 1 停止任务. */
            ScheduledFuture<?> scheduledFuture = SCHEDULED_FUTURE.remove(taskKey);
            scheduledFuture.cancel(false);

            /** 2 重置cron表达式. */
            task.setTaskTrigger(cronExpression);

            /** 3 重启任务. */
            start(task);
        }
    }

    /**
     * 仅执行一次.
     * @param config 所要执行任务.
     */
    public static void onlyOneTime(TaskConfig config, Long delay) throws TaskException{

        if (StringUtils.isEmpty(config.getTaskClass())) {
            throw new TaskException("任务执行类不能为空.");
        }
        try {
            Object taskInstance = AppContext.getBean(config.getTaskClass());
            if(taskInstance != null && taskInstance instanceof Runnable) {
                if(taskInstance instanceof BaseTask){
                    ((BaseTask) taskInstance).setTaskConfig(config);
                }
                ct.execute((Runnable) taskInstance, delay);
            }else{
                throw new TaskException("任务实现类未实现可执行接口");
            }
        }catch (Exception e){
            throw new TaskException("任务实现类加载失败");
        }
    }

    /**
     * 销毁线程池中的任务.
     */
    public static void destrory() {

        LOGGER.info("正在终止自动任务的线程池资源.");
        ScheduledExecutorService scheduledExecutor = (ScheduledExecutorService) ct.getConcurrentExecutor();
        try {
            scheduledExecutor.shutdownNow();
        } catch (Exception e) {
            LOGGER.info("自动任务的线程池资源清理发生异常.", e);
        } finally {
            LOGGER.info("自动任务的线程池资源清理完成.");
        }
    }

    /**
     * 关闭当前所有任务
     */
    public static void stopAll(){

        for(String key : SCHEDULED_FUTURE.keySet()){
            try {
                ScheduledFuture<?> scheduledFuture = SCHEDULED_FUTURE.get(key);
                scheduledFuture.cancel(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SCHEDULED_FUTURE.clear();
    }
}
