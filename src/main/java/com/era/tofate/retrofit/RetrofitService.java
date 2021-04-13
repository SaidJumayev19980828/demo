package com.era.tofate.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitService<S, A> {

    public S mInstance;
    public static Retrofit mRetrofit;

    public RetrofitService(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public S getInstance() {
        throw new UnsupportedOperationException();
    }

    public A getRetrofitApi(){
        throw new UnsupportedOperationException();
    };
}
