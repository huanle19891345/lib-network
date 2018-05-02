package com.pince.network;

import retrofit2.Converter;

public class RetrofitManager {

    public static Converter responseConverter;

    public static OkHttpWrapper.Builder okHttpBuilder() {
        return OkHttpWrapper.builder();
    }

    public static <T> T getService(Class<T> tClass) {
       return RetrofitServiceFactory.getService(tClass);
    }

    public static void replaceResponseConverter(Converter converter) {
        responseConverter = converter;
    }

}
