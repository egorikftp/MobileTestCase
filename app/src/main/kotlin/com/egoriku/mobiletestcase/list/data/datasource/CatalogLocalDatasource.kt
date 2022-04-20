package com.egoriku.mobiletestcase.list.data.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.egoriku.database.CatalogDatabase
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.database.entity.PaginationKeysEntity
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogLocalDatasource

internal class CatalogLocalDatasource(
    private val database: CatalogDatabase
) : ICatalogLocalDatasource {

    private val catalogDao = database.catalogDao()
    private val paginationKeysDao = database.paginationKeysDao()

    override fun pagingSource(): PagingSource<Int, CatalogEntity> = catalogDao.pagingSource()

    override suspend fun cacheResponse(
        data: List<CatalogEntity>,
        prevKey: String?,
        nextKey: String?,
        isRefresh: Boolean
    ) {
        database.withTransaction {
            if (isRefresh) {
                catalogDao.clearAll()
                paginationKeysDao.clearAll()
            }

            paginationKeysDao.insertAll(
                list = data.map { entity ->
                    PaginationKeysEntity(
                        id = entity.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
            )
            catalogDao.insertAll(data)
        }
    }

    override suspend fun getPaginationKeysForId(id: String): PaginationKeysEntity? {
        return paginationKeysDao.getPaginationKeysForId(id = id)
    }
}