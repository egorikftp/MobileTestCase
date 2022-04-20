package com.egoriku.network.koin

import com.egoriku.network.ApiService
import com.egoriku.network.BuildConfig
import com.egoriku.network.interceptor.AuthorizationInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal const val BASE_URL = "https://marlove.net/e/mock/v1/"

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(apiKey = BuildConfig.API_KEY))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create()
    }
}