package com.github.quentin7b.androidboilerplate.okhttp3.interceptor

import com.github.quentin7b.androidboilerplate.network.okhttp3.interceptor.HeaderInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.random.Random


class HeaderInterceptorUnitTest {

    private lateinit var headerName: String
    private lateinit var headerValue: String
    private lateinit var mockServer: MockWebServer

    @Before
    @Throws
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        headerName = "Header-Test-${System.currentTimeMillis()}"
        headerValue = Random.nextInt(1234567).toString(10)
    }

    @After
    @Throws
    fun tearDown() {
        // We're done with tests, shut it down
        mockServer.shutdown()
    }

    @Test
    fun `header name is added by string constructor`() = runBlocking {
        val response = buildResponse(false)
        assertNotNull(response.request.header(headerName))
    }

    @Test
    fun `header name is added by string fetcher constructor`() = runBlocking {
        val response = buildResponse(true)
        assertNotNull(response.request.header(headerName))
    }

    @Test
    fun `header value is correct with string constructor`() = runBlocking {
        val response = buildResponse(false)
        assertEquals(headerValue, response.request.header(headerName))
    }

    @Test
    fun `header value is correct with string fetcher constructor`() = runBlocking {
        val response = buildResponse(true)
        assertEquals(headerValue, response.request.header(headerName))
    }

    private fun buildResponse(isValueFromBlock: Boolean): Response {
        val headerInterceptor = if (isValueFromBlock) {
            HeaderInterceptor(headerName) { headerValue }
        } else {
            HeaderInterceptor(headerName, headerValue)
        }
        mockServer.enqueue(MockResponse())

        return OkHttpClient()
            .newBuilder()
            .addInterceptor(headerInterceptor)
            .build()
            .newCall(
                Request
                    .Builder()
                    .url(mockServer.url("/"))
                    .build()
            )
            .execute()
    }

}