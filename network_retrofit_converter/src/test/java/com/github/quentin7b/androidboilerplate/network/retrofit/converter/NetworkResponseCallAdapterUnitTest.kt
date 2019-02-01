package com.github.quentin7b.androidboilerplate.network.retrofit.converter

import com.github.quentin7b.androidboilerplate.network.NetworkResponse
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkResponseCallAdapterUnitTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var fakeService: FakeService

    @Before
    @Throws
    fun setUp() {
        mockServer = MockWebServer()
        // Start the local server
        mockServer.start()
        // Get an okhttp client
        val service = Retrofit.Builder()
            .addCallAdapterFactory(NetworkResponseCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockServer.url("/fake/"))
            .client(OkHttpClient.Builder().build())
            .build()
        fakeService = service.create(FakeService::class.java)
    }

    @After
    @Throws
    fun tearDown() {
        // We're done with tests, shut it down
        mockServer.shutdown()
    }

    @Test
    fun `list response has correct type if success`() = runBlocking {
        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody("[{\"id\": 1},{\"id\": 2}]")
        mockServer.enqueue(mockResponse)

        val itemsResponse = fakeService.listIt().await()
        when (itemsResponse) {
            is NetworkResponse.Success -> assert(true)
            is NetworkResponse.Error -> assert(false)
        }
    }

    @Test
    fun `list response has correct items if success`() = runBlocking {
        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody("[{\"id\": 1},{\"id\": 2}]")
        mockServer.enqueue(mockResponse)

        val itemsResponse = fakeService.listIt().await()
        when (itemsResponse) {
            is NetworkResponse.Success -> {
                assertEquals(2, itemsResponse.item.size)
                assertEquals(1, itemsResponse.item[0].id)
                assertEquals(2, itemsResponse.item[1].id)
            }
            is NetworkResponse.Error -> assert(false)
        }
    }

    @Test
    fun `list response has correct type if error`() = runBlocking {
        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setHeader("Content-Type", "application/json")
            .setBody("")
        mockServer.enqueue(mockResponse)

        val itemsResponse = fakeService.listIt().await()
        when (itemsResponse) {
            is NetworkResponse.Success -> assert(false)
            is NetworkResponse.Error -> assert(true)
        }
    }

    @Test
    fun `list response has correct status code if error`() = runBlocking {
        // Mock a response with status 200 and sample JSON output
        val statusCode = 400
        val mockResponse = MockResponse()
            .setResponseCode(statusCode)
            .setHeader("Content-Type", "application/json")
            .setBody("")
        mockServer.enqueue(mockResponse)

        val itemsResponse = fakeService.listIt().await()
        val error = itemsResponse as NetworkResponse.Error
        assertEquals(statusCode, error.statusCode)
    }

    @Test
    fun `list response has correct cause if error`() = runBlocking {
        // Mock a response with status 200 and sample JSON output
        val statusCode = 400
        val mockResponse = MockResponse()
            .setResponseCode(statusCode)
            .setHeader("Content-Type", "application/json")
            .setBody("")
        mockServer.enqueue(mockResponse)

        val itemsResponse = fakeService.listIt().await()
        when (itemsResponse) {
            is NetworkResponse.Success -> assert(false)
            is NetworkResponse.Error -> assertEquals(HttpException::class, itemsResponse.cause::class)
        }
    }

    data class FakeItem(
        @SerializedName("id") var id: Int
    )

    interface FakeService {
        @GET("/test")
        fun listIt(): Deferred<NetworkResponse<List<FakeItem>>>
    }

}