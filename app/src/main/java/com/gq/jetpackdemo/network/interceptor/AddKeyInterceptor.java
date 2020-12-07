package com.gq.jetpackdemo.network.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 所有请求添加固定参数
 */
public class AddKeyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl httpUrl = originalRequest.url();
        HttpUrl newUrl = httpUrl.newBuilder()
                .addQueryParameter("apikey", "")
                .build();
        Request.Builder builder = originalRequest.newBuilder().url(newUrl);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
