Android Boilerplate: UseCase
===  

![Hosted on Jitpack.io](https://img.shields.io/badge/hosting-jitpack-blue.svg)

_TL;DR A set of code bunches I don't want to write anymore !_    

## UseCase

`UseCase` is a simple interface that expose a `doIt` method.
`UseCase` is parameterizable by `<I,O>` shorts for **I**nput and **O**utput

## Examples

```kotlin

class MinLengthUseCase(private val minLength): UseCase<String, Boolean> {
    override fun doIt(input: String) = input.length > minLength
}

class HelloWorldUseCase: UseCase<String, Unit> {
    override fun doIt(input: String) {
        Log.i("SuperUseCase", "Hello $input")
    }
}

```

## Install

In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b:android-boilerplate:usecase:1.0.0'    
}    
``` 