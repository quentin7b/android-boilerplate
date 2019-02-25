Android Boilerplate  
===  

![Hosted on Jitpack.io](https://img.shields.io/badge/hosting-jitpack-blue.svg)    

_TL;DR A set of code bunches I don't want to write anymore !_    
 
 ## Elements  
  
- [UseCase](usecase/README.md) A simple `UseCase` interface  
- [Network](network/README.md) A `NetworkResponse` item for safe network call (no more try catch)  
- [Network-Retrofit-Converter](network_retrofit_converter/README.md) A helper for [Retrofit](https://square.github.io/retrofit/) usage with [Network](network/README.md)  
- [Network-Okhttp3-Interceptor](network_okhttp3_interceptors/README.md) A helper for the interceptors for [okhttp3](https://square.github.io/okhttp/) (like to set headers)
  
## Install    

Add it over [jitpack.io](https://jitpack.io/docs/ANDROID/)    
    
In the *project* `build.gradle`    
 ```gradle    
allprojects {    
    repositories {    
        jcenter()    
        // Maybe google()    
        maven { url "https://jitpack.io" }    
    }    
}    
```    
    
In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b.android-boilerplate:MODULE_NAME:MODULE_VERSION'    
}    
``` 

For example the `usecase` module:
`implementation 'com.github.quentin7b.android-boilerplate:usecase:1.0.0'`
    
## License    

 Project is under [Apache 2](LICENSE)    
Feel free to improve by opening an issue or a pull request