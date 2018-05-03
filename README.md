# lib-network
## Android 网络基础组件库， 基于 Retrofit2/OkHttp3 Rxjava2
-  Android network library base on Retrofit2/OkHttp3 Rxjava2,
-  Provide the relative util/helper class to help you create network part easier and not restrict the flexable



# 用法:  
最新版  ![](https://jitpack.io/v/huanle19891345/lib-network.svg)

1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency
```
	dependencies {
	        implementation 'com.github.huanle19891345:lib-network:v{x.y,z}' //x y z is the lastest version name
	}

```

3. Usage in your code:(You can reference the app module sample usage code)
```
 初始化:
          RetrofitManager.okHttpBuilder()  
              .setLogEnable(true)  
              .retrofitBuilder("http://www.weather.com.cn/")  
              .build(); 
```


```
 请求接口:
          RetrofitManager.getService(TestService.class)  
                .getTestModel()  
                .subscribe(new Consumer<TestModel>() {  
                    @Override  
                     public void accept(TestModel dataBean) throws Exception {  
                         Log.d(TAG, dataBean.toString());  
                     }  
                 });  
```

- 支持各种拦截器，转换器的配置
