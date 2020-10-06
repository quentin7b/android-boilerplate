Android Boilerplate: RecyclerView
===  

![Hosted on Jitpack.io](https://img.shields.io/jitpack/v/github/quentin7b/android-boilerplate?label=android-boilerplate)    

_TL;DR A set of code bunches I don't want to write anymore !_    

## RecyclerViewBasicAdapter

`RecyclerViewBasicAdapter` is a class that speeds up the RecyclerView adapter boilerplate

## Examples

```kotlin

recyclerView.adapter = RecyclerViewBasicAdapter(R.layout.item_view) { item, view ->
    view.findViewById(R.id.title) = item.title
}

```

## Install

In the *module* `build.gradle`    
 ```gradle    
dependencies {    
    implementation 'com.github.quentin7b.android-boilerplate:recycler-view:1.0.0'    
}    
``` 