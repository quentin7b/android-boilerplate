package com.github.quentin7b.androidboilerplate.network

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class NetworkResponseTest {

    @Test
    fun `success response can be casted to NetworkResponse_Success`() {
        val response = success("success")
        assertEquals(true, response is NetworkResponse.Success<String>)
    }

    @Test
    fun `success response has the item in its property`() {
        val response = success("success") as NetworkResponse.Success<String>
        assertEquals("success", response.item)
    }

    @Test
    fun `error response can be casted to NetworkResponse_Error`() {
        val response = error<String>(1, Exception())
        assertEquals(true, response is NetworkResponse.Error<String>)
    }

    @Test
    fun `error response has correct status code`() {
        val randomCode = Random.nextInt()
        val response = error<String>(randomCode, Exception()) as NetworkResponse.Error<String>
        assertEquals(randomCode, response.statusCode)
    }

    @Test
    fun `error response has correct cause property`() {
        val message = "This is a test exception"
        val e = Exception(message)
        val response = error<String>(1, e) as NetworkResponse.Error<String>
        assertEquals(e, response.cause)
        assertEquals(message, response.cause.message)
    }

    private fun <T> success(message: T): NetworkResponse<T> {
        return NetworkResponse.Success(message)
    }

    private fun <T> error(code: Int, error: Exception): NetworkResponse<T> {
        return NetworkResponse.Error(code, error)
    }
}
