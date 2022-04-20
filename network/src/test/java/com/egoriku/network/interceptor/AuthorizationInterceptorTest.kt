package com.egoriku.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class AuthorizationInterceptorTest {

    private val TEST_API_KEY = "test_key"

    private var mockWebServer: MockWebServer by Delegates.notNull()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun testAuthorizationInterceptor() {
        mockWebServer.enqueue(MockResponse())

        OkHttpClient().newBuilder()
            .addInterceptor(AuthorizationInterceptor(apiKey = TEST_API_KEY))
            .build()
            .newCall(
                Request.Builder()
                    .url(mockWebServer.url("/"))
                    .build()
            ).execute()

        val request = mockWebServer.takeRequest()

        assertEquals(TEST_API_KEY, request.getHeader("Authorization"))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}