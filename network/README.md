Android Boilerplate: Network
===  

![Hosted on Jitpack.io](https://img.shields.io/badge/hosting-jitpack-blue.svg)

_TL;DR A set of code bunches I don't want to write anymore !_    

## UseCase

`NetworkResponse` is a parametrized sealed class that has two subclasses `Success` and `Error`, it is designed to say No to if/else and try/catch

- `Success` is a subclass that has 1 property `item` which is the success object
- `Error` is a subclass that has 2 properties `statusCode` which is the HTTP status code of the failure and `cause` which is the Throwable that caused the failure

## Examples

```kotlin

fun doANetworkCallAndUseIt() {
    val networkResult: NetworkResponse<Item> = myRepository.fetchThisItem()
    when (networkResult) {
        is NetworkResponse.Success -> useIt(networkResult.item) // Smart cast to NetworkResponse.Success<Item>
        is NetworkResponse.Error -> { // Smart cast to NetworkResponse.Error<Item>
            MyAwesomeLogger.e(error.cause, "Oops, cant fetchThisItem")    
            handleErrorCode(error.statusCode)    
        }
    }
}

```

## Install

In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b.android-boilerplate:network:1.0.0'    
}    
``` 
