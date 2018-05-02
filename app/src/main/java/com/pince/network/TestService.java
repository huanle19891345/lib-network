package com.pince.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TestService {

    @GET("data/sk/101010100.html")
    Observable<TestModel> getTestModel();

}
