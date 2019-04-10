package com.hbtl.yhb.http;


import com.hbtl.yhb.modles.CountModel;
import com.hbtl.yhb.modles.UserModel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

//所有的接口在此
public interface ApiService {
    @GET()
    @Streaming
        //使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    Flowable<ResponseBody> download(@Url String url);  //返回值使用 ResponseBody 之后会对ResponseBody 进行读取


    @POST("/channel/login")
    @FormUrlEncoded
    Observable<Result<UserModel>> login(@Field("user_name") String user_name, @Field("password") String password);


    @POST("/channel/channel_recycle")
    @FormUrlEncoded
    Observable<Result<CountModel>> scanCode(@Field("token") String token, @Field("code") String code);

}
