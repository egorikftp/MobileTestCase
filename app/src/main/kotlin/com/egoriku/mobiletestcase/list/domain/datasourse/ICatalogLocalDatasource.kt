package com.egoriku.mobiletestcase.list.domain.datasourse

import androidx.paging.PagingSource
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.database.entity.PaginationKeysEntity

interface ICatalogLocalDatasource {

    fun pagingSource(): PagingSource<Int, CatalogEntity>

    suspend fun cacheResponse(
        data: List<CatalogEntity>,
        prevKey: String?,
        nextKey: String?,
        isRefresh: Boolean
    )

    suspend fun getPaginationKeysForId(id: String): PaginationKeysEntity?
}