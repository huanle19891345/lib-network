package com.pince.network;

import android.content.Context;

import com.pince.network.interceptor.AddCookiesInterceptor;
import com.pince.network.interceptor.HeaderInterceptor;
import com.pince.network.interceptor.ReceivedCookiesInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpWrapper {

    private OkHttpClient client;


    public OkHttpWrapper(Builder builder) {
        this.client = builder.client;
    }

    public OkHttpClient getClient() {
        return client;
    }


    public static Builder builder(Context context){
        return new OkHttpWrapper.Builder(context);
    }

    public static class Builder {
        private Context context;
        private boolean logEnable;
        private Map<String, String> headerParams = new HashMap<>();

        private Interceptor logInterceptor;
        private OkHttpClient client;

        Builder(Context context) {
            this.context = context;
        }

        public Builder setLogEnable(boolean logEnable) {
            this.logEnable = logEnable;
            return this;
        }

        public Builder setHeaderParams(Map<String, String> headerParams) {
            this.headerParams = headerParams;
            return this;
        }

        public OkHttpWrapper build() {
            logInterceptor = new HttpLoggingInterceptor()
                    .setLevel(logEnable ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            client = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .addNetworkInterceptor(new HeaderInterceptor(headerParams))
                    .addInterceptor(new ReceivedCookiesInterceptor(context))
                    .addInterceptor(new AddCookiesInterceptor(context))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            return new OkHttpWrapper(this);
        }

        public RetrofitWrapper.Builder retrofitBuilder(String baseUrl) {
            return new RetrofitWrapper.Builder(baseUrl).setOkHttpWrapper(build());
        }
    }


}
