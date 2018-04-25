package com.mingtai.base.util.http;

import com.mingtai.base.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkzc-mcy create at 2018/4/3.
 * url请求工具类
 */
public class HttpRequest {

    static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    public static final String GET = "GET";
    public static final String POST = "POST";

    private String url;
    private String method = "GET";
    private Map<String, Object> params;
    private Map<String,String> headers;
    private String body;

    public HttpRequest(String url){
        this.url = url;
    }

    /** 设置发请求方法 */
    public HttpRequest setMethod(String method){
        this.method = method;
        return this;
    }

    /** 直接设置post请求发送内容 */
    public HttpRequest setBody(String body){
        this.body = body;
        return this;
    }

    /**
     * 设置单个参数
     */
    public HttpRequest setParam(String key, Object value){
        if(params == null){
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    /**
     * 设置多个参数
     */
    public HttpRequest setParams(Map<String, Object> params){
        for(String key : params.keySet()){
            setParam(key, params.get(key));
        }
        return this;
    }

    /** 设置单个头信息 */
    public HttpRequest setHeader(String key, String value){
        if(headers == null){
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    /** 设置多个头信息 */
    public HttpRequest setHeaders(Map<String,String> headers){
        for(String key : headers.keySet()){
            setHeader(key, headers.get(key));
        }
        return this;
    }


    /** 执行请求 */
    public String request() throws IOException {

        // GET 请求拼接url
        if(GET.equals(this.method)) {
            if(params != null && params.size() > 0) {
                url = url + "?" + getParamsString();
            }
        }

        URL realURL = new URL(url);

        LOGGER.info(realURL.toString());

        URLConnection conn = realURL.openConnection();

        // 设置通用的请求头属性
        if(headers != null){
            for(String key : headers.keySet()){
                conn.setRequestProperty(key,headers.get(key));
            }
        }

        // 设置请求方式
        if(POST.equals(this.method)){
            // 设置是否向connection输出，post请求参数要放在http正文内，因此需要设为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);

            // 写入post数据
            PrintWriter writer = new PrintWriter(conn.getOutputStream());
            if(StringUtils.isBlank(body)) {
                writer.print(getParamsString());
            }else{
                writer.print(body);
            }
            writer.flush();
            writer.close();
        }

        // 读取数据
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String line = br.readLine();
        while (line != null){
            result.append(line+"\n");
            line = br.readLine();
        }

        br.close();
        return result.toString();
    }

    /**
     * 构造参数字符串
     * @return
     */
    private String getParamsString() {

        StringBuilder urlParams = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                if(params.get(key) != null && !"".equals(params.get(key))){
                    if (urlParams.length() > 0) {
                        urlParams.append("&");
                    }
                    urlParams.append(key);
                    urlParams.append("=");
                    urlParams.append(params.get(key));
                }
            }
        }
        return urlParams.toString();
    }
}
