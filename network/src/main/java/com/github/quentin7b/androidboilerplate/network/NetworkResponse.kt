package com.github.quentin7b.androidboilerplate.network

/**
 * This class is meant to be used as a response from an API call
 * The main goal is to have a unique class to handle the Success or the Error
 */
sealed class NetworkResponse<O> {

    /**
     * Success class
     */
    data class Success<O>(val item: O) : NetworkResponse<O>()

    /**
     * Error class
     */
    data class Error<O>(val statusCode: Int, val cause: Throwable) :
        NetworkResponse<O>()
}