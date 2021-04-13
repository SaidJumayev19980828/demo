package com.era.tofate.retrofit.firebase;

import com.era.tofate.payload.firebase.UsersFirebaseRequest;
import com.era.tofate.payload.firebase.UsersFirebaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FirebaseRetrofitApi {

    @POST("v1/accounts:lookup")
    Call<UsersFirebaseResponse> getUser(@Header("Content-Type") String contentType,  @Query("key") String apiKey, @Body UsersFirebaseRequest usersFirebaseRequest);

}
