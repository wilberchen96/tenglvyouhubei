package com.hbtl.yhb.http;


import com.hbtl.yhb.configs.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxHttp {
    //读写超时
    private static final int readTimeOut = 15;
    //连接超时
    private static final int connectTimeOut = 15;

    private static RxHttp mInstance;

    private Map<String, Retrofit> mRetrofitMap = new HashMap<>();

    private RxHttp() {
    }

    public static RxHttp getInstance() {
        RxHttp result = null;
        if (mInstance != null) {
            result = mInstance;
        } else {
            synchronized (RxHttp.class) {
                mInstance = new RxHttp();
                result = mInstance;
            }
        }
        return result;
    }
    public ApiService getApi() {
        return getRetrofit(Config.server_domain).create(ApiService.class);
    }

    public ApiService getApi(String url) {
        return getRetrofit(url).create(ApiService.class);
    }

    public ApiService getApi(String url, Interceptor... interceptor) {
        return getRetrofit(url, interceptor).create(ApiService.class);
    }

    public Retrofit getRetrofit(String serverUrl) {
        Retrofit retrofit;
        if (mRetrofitMap.containsKey(serverUrl)) {
            retrofit = mRetrofitMap.get(serverUrl);
        } else {
            retrofit = createRetrofit(serverUrl);
            mRetrofitMap.put(serverUrl, retrofit);
        }
        return retrofit;
    }

    public Retrofit getRetrofit(String serverUrl, Interceptor... interceptors) {
        Retrofit retrofit;
        if (mRetrofitMap.containsKey(serverUrl)) {
            retrofit = mRetrofitMap.get(serverUrl);
        } else {
            retrofit = createRetrofit(serverUrl, interceptors);
            mRetrofitMap.put(serverUrl, retrofit);
        }
        return retrofit;
    }


    private Retrofit createRetrofit(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        ProgressManager.getInstance().with(builder);
        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private Retrofit createRetrofit(String baseUrl, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        ProgressManager.getInstance().with(builder);
        if (interceptors != null && interceptors.length > 0) {
            for (int i = 0; i < interceptors.length; i++) {
                if (interceptors[i] != null) {
                    builder.addInterceptor(interceptors[i]);
                }
            }
        }
        OkHttpClient client = builder
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
