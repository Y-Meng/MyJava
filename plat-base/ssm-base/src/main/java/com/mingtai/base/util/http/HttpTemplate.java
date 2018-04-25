package com.mingtai.base.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkzc-mcy create at 2018/4/3.
 */
public class HttpTemplate {

    static final Logger LOGGER = LoggerFactory.getLogger(HttpTemplate.class);

    public static void get(String url, Callback callback){
        try {
            String result = new HttpRequest(url).request();
            if(callback != null){
                callback.onSuccess(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            if(callback != null){
                callback.onFail(e);
            }
        }
    }

    public static void get(String url, Map<String, Object> params, Callback callback){
        try {
            String result = new HttpRequest(url)
                    .setParams(params)
                    .request();
            if(callback != null){
                callback.onSuccess(result);
            }
        }catch (IOException e){
            e.printStackTrace();
            LOGGER.error(e.toString());
            if(callback != null){
                callback.onFail(e);
            }
        }
    }

    public static void post(String url, Map<String, Object> params, Callback callback){
        try {
            String result = new HttpRequest(url)
                    .setMethod(HttpRequest.POST)
                    .setParams(params)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .request();
            if(callback != null){
                callback.onSuccess(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            if(callback != null){
                callback.onFail(e);
            }
        }
    }

    public static void postJson(String url, String body, Callback callback){
        try {
            String result = new HttpRequest(url)
                    .setMethod(HttpRequest.POST)
                    .setBody(body)
                    .setHeader("Content-Type", "application/json;charset=utf-8")
                    .request();
            if(callback != null){
                callback.onSuccess(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            if(callback != null){
                callback.onFail(e);
            }
        }
    }

    public interface Callback{
        /** 处理返回数据 */
        void onSuccess(String result);
        void onFail(Exception error);
    }
}
