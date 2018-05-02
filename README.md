# lib-network
Android 网络基础组件库， 基于 Retrofit2/OkHttp3 Rxjava2 。
* Android network library base on Retrofit2/OkHttp3 Rxjava2,
  provide the relative util/helper class to help you create network part easier and not restrict the flexable

* 调用形式:
* 初始化:
*         RetrofitManager.okHttpBuilder()
*                .setLogEnable(true)
*                .retrofitBuilder("http://www.weather.com.cn/")
*                 .build();

* 请求接口:
*         RetrofitManager.getService(TestService.class)
*                .getTestModel()
*                .subscribe(new Consumer<TestModel>() {
*                    @Override
*                    public void accept(TestModel dataBean) throws Exception {
*                        Log.d(TAG, dataBean.toString());
*                    }
*                });


* 支持各种拦截器，转换器的配置