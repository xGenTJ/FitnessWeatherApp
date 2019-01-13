package com.example.jingt.testp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String Base_URL="https://api.mobile.endomondo.com/mobile/";
    private static RetroClient rInstance;
    private Retrofit retrofit;

    private RetroClient(){                                          //kinda a factory method that creates an Object of our interest for us
        retrofit= new retrofit2.Retrofit.Builder()                 //also a builder pattern, u specify all goodies you want
                .baseUrl(Base_URL)                                  //and u get the object of ur desired configuration
                .addConverterFactory(GsonConverterFactory.create())  //the builder doing all the work for us in creating the object with desired properties
                .build();                                            //all my desired options wrapped up in a nice retrofit object
    }

    public static synchronized RetroClient getRetroInstant(){
        if (rInstance==null){                                       //additionally using a singleton pattern
            rInstance=new RetroClient(); }                          //only one retrofit object @ given time
        return rInstance;
    }

    public endoApi getApi(){
        return retrofit.create(endoApi.class);
    }
}
