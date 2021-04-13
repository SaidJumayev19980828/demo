package com.era.tofate.retrofit.firebase;

import com.era.tofate.retrofit.RetrofitService;

public class FirebaseRetrofitService extends RetrofitService<FirebaseRetrofitService, FirebaseRetrofitApi> {

    private final static String BASE_URL = "https://identitytoolkit.googleapis.com/";

    public FirebaseRetrofitService() {
        super(BASE_URL);
    }

    @Override
    public FirebaseRetrofitApi getRetrofitApi() {
        return mRetrofit.create(FirebaseRetrofitApi.class);
    }

    @Override
    public FirebaseRetrofitService getInstance() {
        if (mInstance == null) {
            mInstance = new FirebaseRetrofitService();
        }
        return mInstance;
    }
}
