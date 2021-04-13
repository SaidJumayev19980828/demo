package com.era.tofate.retrofit;

import com.era.tofate.retrofit.firebase.FirebaseRetrofitService;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class RetrofitFactory<S> {

    public S create(RetrofitType type) {

        RetrofitService retrofitService = null;
        switch (type) {
            case FIREBASE:
                retrofitService = new FirebaseRetrofitService();
                break;
        }
        return (S) retrofitService;
    }

}
