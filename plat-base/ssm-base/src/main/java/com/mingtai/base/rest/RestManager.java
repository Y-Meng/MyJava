package com.mingtai.base.rest;

import com.mingtai.base.model.ApiResult;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkzc-mcy create at 2018/4/12.
 */
public class RestManager {

    static Map<String, RestAdapter> adapters = new HashMap<>();

    private RestManager(){}

    public static synchronized RestAdapter getAdapter(String baseUrl){
        RestAdapter adapter = adapters.get(baseUrl);
        if(adapter == null){
            adapter = new RestAdapter.Builder().setEndpoint(baseUrl).build();
            adapters.put(baseUrl, adapter);
        }
        return adapter;
    }

    public static void main(String[] args){
        String baseUrl = "http://localhost:9091/";
        IOpenApi api = RestManager.getAdapter(baseUrl).create(IOpenApi.class);

        System.out.println(api.getRelate("美团大战滴滴").getData().toString());
    }

    public interface IOpenApi {

        /**
         * 获取联想词
         * @param keyword
         * @return
         */
        @GET("/search/relate")
        ApiResult getRelate(@Query("keyword") String keyword);
    }
}