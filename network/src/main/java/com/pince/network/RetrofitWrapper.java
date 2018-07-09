package com.pince.network;


import com.pince.network.adapter.RxThreadCallAdapter;
import com.pince.network.converter.CustomConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {

    public RetrofitWrapper() {
    }

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static Builder builder(String baseUrl, OkHttpWrapper okHttpWrapper) {
        return new Builder(baseUrl, okHttpWrapper);
    }

    public static class Builder {
        private String baseUrl;
        private OkHttpWrapper okHttpWrapper;

        public Builder(String baseUrl, OkHttpWrapper okHttpWrapper) {
            this.baseUrl = baseUrl;
            this.okHttpWrapper = okHttpWrapper;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public OkHttpWrapper getOkHttpWrapper() {
            return okHttpWrapper;
        }

        public void build() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(CustomConverterFactory.create())
                    .addCallAdapterFactory(new RxThreadCallAdapter(Schedulers.io(), AndroidSchedulers.mainThread()))
                    .client(okHttpWrapper.getClient())
                    .build();
        }
    }
}
