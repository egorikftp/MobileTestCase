package com.egoriku.mobiletestcase.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogLocalDatasource
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogNetworkDatasource
import com.egoriku.mobiletestcase.list.domain.repository.ICatalogRepository

internal class CatalogRepository(
    private val networkDatasource: ICatalogNetworkDatasource,
    private val localDatasource: ICatalogLocalDatasource
) : ICatalogRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun remoteMediator(): RemoteMediator<Int, CatalogEntity> {
        return CatalogRemoteMediator(
            networkDatasource = networkDatasource,
            localDatasource = localDatasource
        )
    }

    override fun pagingSource(): PagingSource<Int, CatalogEntity> {
        return localDatasource.pagingSource()
    }
}