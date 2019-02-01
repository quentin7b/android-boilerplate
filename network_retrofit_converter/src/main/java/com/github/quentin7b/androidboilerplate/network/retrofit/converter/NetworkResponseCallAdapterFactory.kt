package com.github.quentin7b.androidboilerplate.network.retrofit.converter

import com.github.quentin7b.androidboilerplate.network.NetworkResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Mostly inspired from https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter
 */
class NetworkResponseCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() =
            NetworkResponseCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred<NetworkResponse> return type must be parameterized as Deferred<NetworkResponse<Foo>> or Deferred<NetworkResponse<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)
        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == NetworkResponse::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "NetworkResponse must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>"
                )
            }
            NetworkResponseCallAdapter<Any>(
                getParameterUpperBound(0, responseType)
            )
        } else {
            throw java.lang.IllegalStateException("Cant find an adapter that matches the output : $rawDeferredType")
        }
    }

    private class NetworkResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<NetworkResponse<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<NetworkResponse<T>> {
            val deferred = CompletableDeferred<NetworkResponse<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.complete(NetworkResponse.Error(-1, t))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        deferred.complete(NetworkResponse.Success(response.body()!!))
                    } else {
                        deferred.complete(NetworkResponse.Error(response.code(), HttpException(response)))
                    }
                }
            })

            return deferred
        }
    }
}