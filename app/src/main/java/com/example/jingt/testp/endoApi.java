package com.example.jingt.testp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface endoApi {

    @FormUrlEncoded
    @POST("auth")
    Call<ResponseBody> getToken(
            @Field("email") String email,
            @Field("password") String password,
            @Field("deviceId") String deviceId,
            @Field("action") String action,
            @Field("country") String country
    );

    @FormUrlEncoded
    @POST("api/workout/list")
    Call<WorkOutSet> getWork(
            @Field("authToken") String authToken
    );

    @GET("api/workout/list?authToken=1NklxWocQcmqSJXaD0L8Xw")
    Call<WorkOutSet> getWorkOut(                            //Call 'an object' provided to us by retrofit accepts a eneric type
                                                            @Query("authToken") String authToken);          // JSON response recevied will be that of type WorkOutSet 'a class
}                                                           // that models data to be received from Endomondo API'
                                                            //@Query("authToken") String authToken