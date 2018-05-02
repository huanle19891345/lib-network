package com.pince.network;

import android.app.Application;
import android.util.Log;

import io.reactivex.functions.Consumer;


public class MainApplication extends Application {

    public static final String TAG = "HttpLog";

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitManager.okHttpBuilder()
                .setLogEnable(true)
                .retrofitBuilder("http://www.weather.com.cn/")
                .build();

        RetrofitManager.getService(TestService.class)
                .getTestModel()
               .subscribe(new Consumer<TestModel>() {
                   @Override
                   public void accept(TestModel dataBean) throws Exception {
                       Log.d(TAG, dataBean.toString());
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Exception {
                       Log.d(TAG, throwable.getMessage());
                   }
               });



    }
}
