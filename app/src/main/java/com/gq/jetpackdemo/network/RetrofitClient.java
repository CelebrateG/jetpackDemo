package com.gq.jetpackdemo.network;

import com.gq.jetpackdemo.network.interceptor.MoreBaseUrlInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.douban.com";
    private static final String ApiKey = "0df993c66c0c636e29ecbb5344252a4a";


    public OkHttpClient getClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new MoreBaseUrlInterceptor()) //BaseUrl拦截器
                .connectTimeout(15, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(15, TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//写入超时时间
                .retryOnConnectionFailure(true)//连接不上是否重连,false不重连
                .build();
    }

    private Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .client(getClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
