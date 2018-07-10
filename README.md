# lib-network
## Android network library base on Retrofit2/OkHttp3 Rxjava2,
-  Provide the relative util/helper class to help you create network part easier and not restrict the flexable



# Usage:  
lastest  ![](https://jitpack.io/v/huanle19891345/lib-network.svg)

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
 init:
          RetrofitManager.okHttpBuilder()  
              .setLogEnable(true)  
              .retrofitBuilder("http://www.weather.com.cn/")  
              .build(); 
```


```
 request api:
          RetrofitManager.getService(TestService.class)  
                .getTestModel()  
                .subscribe(new Consumer<TestModel>() {  
                    @Override  
                     public void accept(TestModel dataBean) throws Exception {  
                         Log.d(TAG, dataBean.toString());  
                     }  
                 });  
```

- provide 
```
interceptters 
   AddCookiesIntercepteor
   ReceivedCookiesIntercepter
   HeaderInterceptor
```
```
 converter:
    CustomConverterFactory
```
```
 adapter:
    RxThreadCallAdapter
```
