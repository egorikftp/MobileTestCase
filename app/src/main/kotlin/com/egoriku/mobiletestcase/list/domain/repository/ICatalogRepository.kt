package com.egoriku.mobiletestcase.list.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.egoriku.database.entity.CatalogEntity

interface ICatalogRepository {

    @OptIn(ExperimentalPagingApi::class)
    fun remoteMediator(): RemoteMediator<Int, CatalogEntity>

    fun pagingSource(): PagingSource<Int, CatalogEntity>
}