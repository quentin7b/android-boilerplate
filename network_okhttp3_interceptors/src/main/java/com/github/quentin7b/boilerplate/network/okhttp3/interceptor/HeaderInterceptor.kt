package com.github.quentin7b.boilerplate.network.okhttp3.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * HeaderInterceptor is used to provide a custom header to a request
 * @property name the name of the header to add.
 * @property valueFetcher a lambda function that provides a string, which is the value of the header
 * @constructor Creates a HeaderInterceptor that will add the header to each request fetching the value each time
 *
 */
class HeaderInterceptor(private val name: String, private val valueFetcher: () -> String) : Interceptor {

    /**
     * Value constructor.
     * @param name the name of the header to add.
     * @param value the value of the header
     * @constructor Creates a HeaderInterceptor that will add the header to each request fetching the value once for all
     * Note that a Lambdas function to obtain the value is created and called each time a request is made
     */
    constructor(name: String, value: String) :
            this(name, { value })

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .header(name, valueFetcher.invoke())
                .build()
        )
    }
}