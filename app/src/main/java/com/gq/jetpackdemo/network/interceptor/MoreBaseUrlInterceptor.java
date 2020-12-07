package com.gq.jetpackdemo.network.interceptor;

import java.io.IOException;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Auther:gq
 * @Desc:baseUrl拦截器
 * @Date:2019/4/17
 */
public class MoreBaseUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的request
        Request originalRequest = chain.request();
        //获取老的url
        HttpUrl oldUrl = originalRequest.url();

        Request.Builder builder = originalRequest.newBuilder();
        //获取头信息 key 为 urlName 的集合
        List<String> urlNameList = originalRequest.headers("urlName");
        if (urlNameList != null && urlNameList.size() > 0) {
            //删除原有配置中的值
            builder.removeHeader("urlName");
            //获取头信息中配置的value,如：test 或者 qiNiu
            String urlName = urlNameList.get(0);
            HttpUrl baseURL = null;
            //根据头信息中配置的value,来匹配新的base_url地址
            switch (urlName) {
                case "douban":
                    baseURL = HttpUrl.parse("");
                    break;
            }

            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())
                    .host(baseURL.host())
                    .port(baseURL.port())
                    .build();

            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);

        } else {
            return chain.proceed(originalRequest);
        }
    }
}
