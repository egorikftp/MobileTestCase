package com.egoriku.mobiletestcase.list.domain.datasourse

import com.egoriku.network.response.CatalogResponse

interface ICatalogNetworkDatasource {

    suspend fun loadInitial(): List<CatalogResponse>

    suspend fun loadAppend(key: String?): List<CatalogResponse>

    suspend fun loadPrepend(key: String?): List<CatalogResponse>
}