package com.mingtai.base.task;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zkzc-mcy on 2017/11/13.
 */
@Controller
@RequestMapping("/auto/task")
public class TaskController {

    /**描述：获取任务列表*/
    @RequestMapping("/list.do")
    @ResponseBody
    public JSONObject listTask(HttpSession session, HttpServletResponse response){
        JSONObject apiResult = new JSONObject();

        try{
            apiResult.put("data", TaskManager.getAllTaskInfo());
            apiResult.put("success",true);
        }catch(Exception e){
            e.printStackTrace();
            apiResult.put("message",e.getMessage());
            apiResult.put("success",false);
        }

        return apiResult;
    }

    /**描述：更新任务状态*/
    @RequestMapping("/update.do")
    @ResponseBody
    public JSONObject updateTask(TaskConfig taskConfig, HttpServletResponse response){
        JSONObject apiResult = new JSONObject();

        if(taskConfig == null || taskConfig.getTaskClass() == null){
            apiResult.put("success", false);
            apiResult.put("message","参数错误");
            return apiResult;
        }

        try{
            TaskManager.updateTaskInfo(taskConfig);
            apiResult.put("success", true);
        }catch(Exception e){
            e.printStackTrace();
            apiResult.put("message", e.getMessage());
            apiResult.put("success", false);
        }

        return apiResult;
    }
}
