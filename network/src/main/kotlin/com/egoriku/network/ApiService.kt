package com.egoriku.network

import com.egoriku.network.response.CatalogResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("items")
    suspend fun load(
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
    ): List<CatalogResponse>
}