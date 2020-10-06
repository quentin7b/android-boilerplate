Android Boilerplate: Network Retrofit Converter
===  

![Hosted on Jitpack.io](https://img.shields.io/jitpack/v/github/quentin7b/android-boilerplate?label=android-boilerplate)    

_TL;DR A set of code bunches I don't want to write anymore !_    

## UseCase

`NetworkResponseCallAdapterFactory` is a call adapter for Retrofit that make possible to have a `Deferred<NetworkResponse<T>>` as an output

## Examples

Now your service interface could send back some `Deferred<NetworkResponse<T>`
```kotlin
interface YourService {
    @GET("/something")
    fun listIt(): Deferred<NetworkResponse<WhatYouWant>>
}
```

All you have to do is add the `NetworkResponseCallAdapterFactory` to the retrofit builder 
```kotlin
val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(NetworkResponseCallAdapterFactory())
            .otherThings()
            .build()
val service = service.create(YourService::class.java)
```

## Install

In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b.android-boilerplate:network_retrofit_converter:1.0.0'    
}    
``` 
