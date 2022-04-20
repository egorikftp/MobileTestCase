package com.egoriku.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create
import kotlin.properties.Delegates

class ApiServiceTest {

    private var mockWebServer: MockWebServer by Delegates.notNull()
    private var apiService: ApiService by Delegates.notNull()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ApiService loading test`() = runTest {
        enqueueMockResponse("response.json")

        val responseBody = apiService.load(maxId = null, sinceId = null)

        val request = mockWebServer.takeRequest()

        assertEquals("/items", request.path)

        assertNotNull(responseBody)
        assertEquals(10, responseBody.size)
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))

            mockWebServer.enqueue(mockResponse)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}