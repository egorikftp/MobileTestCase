package com.egoriku.mobiletestcase.list.data.datasource

import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogNetworkDatasource
import com.egoriku.network.ApiService
import com.egoriku.network.response.CatalogResponse

internal class CatalogNetworkDatasource(
    private val apiService: ApiService
) : ICatalogNetworkDatasource {

    override suspend fun loadInitial(): List<CatalogResponse> {
        return apiService.load(maxId = null, sinceId = null)
    }

    override suspend fun loadAppend(key: String?): List<CatalogResponse> {
        return apiService.load(maxId = key, sinceId = null)
    }

    override suspend fun loadPrepend(key: String?): List<CatalogResponse> {
        return apiService.load(maxId = null, sinceId = key)
    }
}