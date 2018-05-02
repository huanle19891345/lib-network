package com.pince.network;

import java.io.IOException;
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

    private Interceptor logInterceptor;
    private Interceptor headerInterceptor;
    private OkHttpClient client;


    public OkHttpWrapper(Builder builder) {
        this.logInterceptor = builder.logInterceptor;
        this.headerInterceptor = builder.headerInterceptor;
        this.client = builder.client;
    }

    public OkHttpClient getClient() {
        return client;
    }


    public static Builder builder(){
        return new OkHttpWrapper.Builder();
    }

    public static class Builder {
        private boolean logEnable;
        private Map<String, String> headerParams;

        private Interceptor logInterceptor;
        private Interceptor headerInterceptor;
        private OkHttpClient client;


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
                    .setLevel(logEnable ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
            headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = (null == headerParams) ? chain.request() : addHeader(chain.request(), headerParams);
                    return chain.proceed(request);
                }
            };
            client = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .addNetworkInterceptor(headerInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            return new OkHttpWrapper(this);
        }

        public RetrofitWrapper.Builder retrofitBuilder(String baseUrl) {
            return new RetrofitWrapper.Builder(baseUrl).setOkHttpWrapper(build());
        }
    }


    private static Request addHeader(Request original, Map<String, String> headerParams) {
        Request.Builder builder = original.newBuilder();
        Set<Map.Entry<String, String>> items = headerParams.entrySet();
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> item = (Map.Entry<String, String>) iterator.next();
            builder.header(item.getKey(), item.getValue());
        }
        return builder.method(original.method(), original.body())
                .build();
    }


}
