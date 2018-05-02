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

    public static class Builder {
        private String baseUrl;
        private OkHttpWrapper okHttpWrapper;

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public OkHttpWrapper getOkHttpWrapper() {
            return okHttpWrapper;
        }

        public Builder setOkHttpWrapper(OkHttpWrapper okHttpWrapper) {
            this.okHttpWrapper = okHttpWrapper;
            return this;
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
