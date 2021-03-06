package com.mingtai.base.util.http;

import com.mingtai.base.util.StringUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkzc-mcy create at 2018/4/3.
 * url请求工具类
 */
public class HttpRequest {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";

    public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";

    private String userAgent;

    private String url;
    private String method = "GET";
    private Map<String, Object> params;
    private Map<String,String> headers;
    private String body;
    private int connectTimeout = 30000;
    private int readTimeout = 60000;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpRequest setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public HttpRequest setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

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

    /** 设置代理 */
    public HttpRequest setUserAgent(String userAgent){
        this.userAgent = userAgent;
        return this;
    }

    /** 执行请求，返回字符串结果 */
    public String request() throws IOException {

        return request("utf-8");
    }

    /** 执行请求，返回字符串结果 */
    public String request(String charsetName) throws IOException{

        InputStream inputStream = getStream();

        // 读取数据
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charsetName));
        String line = br.readLine();
        while (line != null){
            result.append(line);
            line = br.readLine();
        }

        br.close();
        return result.toString();
    }

    /** 获取接口输入流 */
    public InputStream getStream() throws IOException {

        // GET 请求拼接url
        if(GET.equals(this.method) || DELETE.equals(this.method) || PUT.equals(this.method)) {
            if(params != null && params.size() > 0) {
                url = url + "?" + getParamsString();
            }
        }

        URL realURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();

        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setRequestMethod(this.method);

        // 浏览器伪装
        if(StringUtils.isNotBlank(userAgent)){
            conn.setRequestProperty("User-Agent", userAgent);
        }

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

            // 写入post数据；注意编码：utf-8
            OutputStreamWriter writer = new OutputStreamWriter (conn.getOutputStream(),"utf-8");
            if(StringUtils.isBlank(body)) {
                writer.write(getParamsString());
            }else{
                writer.write(body);
            }
            writer.flush();
            writer.close();
        }

        return conn.getInputStream();
    }

    /**
     * 构造参数字符串
     * @return
     */
    private String getParamsString() throws UnsupportedEncodingException {

        StringBuilder urlParams = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                if(params.get(key) != null && !"".equals(params.get(key))){
                    if (urlParams.length() > 0) {
                        urlParams.append("&");
                    }
                    urlParams.append(key);
                    urlParams.append("=");
                    urlParams.append(URLEncoder.encode(params.get(key).toString(), "utf8"));
                }
            }
        }
        return urlParams.toString();
    }
}
