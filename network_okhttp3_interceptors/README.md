Android Boilerplate: Network Okhttp Interceptors
===  

![Hosted on Jitpack.io](https://img.shields.io/jitpack/v/github/quentin7b/android-boilerplate?label=android-boilerplate)    

_TL;DR A set of code bunches I don't want to write anymore !_    

## UseCase

`HeaderInterceptor` is a helper to set custom headers on an Okhttp client

## Examples

You want to provide an API Key to you requests

```kotlin
return OkHttpClient()
            .newBuilder()
            .addInterceptor(HeaderInterceptor("x-api-key", BuildConfig.API_KEY))
            .build()
            .execute()
```

And it's done, the API Key will be added as a header to each request.

There is more, if you want to put something that change with time, you also can 

```kotlin
return OkHttpClient()
            .newBuilder()
            .addInterceptor(HeaderInterceptor("Authorization") {
                "Bearer ${preferences.getToken()}"
            })
            .build()
            .execute()
```

This way, before each request, the interceptor will look for the value of `preferences.getToken()` and add it to the *Authorization* header

## Install

In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b.android-boilerplate:network_okhttp3_interceptors:1.0.0'    
}    
``` 
