package com.jash.listmediademo;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jash
 * Date: 16-1-7
 * Time: 上午11:12
 */
public class HttpUtils {
    public interface Service{
        @GET("/article/list/video")
        Call<Entity> getVideo(@Query("page") int page);
    }
    private static Service service;
    static {
        service = new Retrofit.Builder()
                .baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Service.class);
    }

    public static Service getService() {
        return service;
    }
}
