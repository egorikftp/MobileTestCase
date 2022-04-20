package com.egoriku.mobiletestcase.list.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.mobiletestcase.list.data.mapper.CatalogResponseToEntityMapper
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogLocalDatasource
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogNetworkDatasource
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
internal class CatalogRemoteMediator(
    private val networkDatasource: ICatalogNetworkDatasource,
    private val localDatasource: ICatalogLocalDatasource,
) : RemoteMediator<Int, CatalogEntity>() {

    private val mapper = CatalogResponseToEntityMapper()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatalogEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    val paginationKeys = state.getRemoteKeyForFirstItem()

                    paginationKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = paginationKeys != null)
                }
                LoadType.APPEND -> {
                    val paginationKeys = state.getRemoteKeyForLastItem()

                    paginationKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = paginationKeys != null)
                }
            }

            val response = when (loadType) {
                LoadType.APPEND -> networkDatasource.loadAppend(key = loadKey)
                LoadType.PREPEND -> networkDatasource.loadPrepend(key = loadKey)
                LoadType.REFRESH -> networkDatasource.loadInitial()
            }.map(transform = mapper)

            val prevKey = response.firstOrNull()?.id
            val nextKey = response.lastOrNull()?.id

            localDatasource.cacheResponse(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey,
                isRefresh = loadType == LoadType.REFRESH
            )

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun PagingState<Int, CatalogEntity>.getRemoteKeyForLastItem() =
        pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { entity -> localDatasource.getPaginationKeysForId(entity.id) }

    private suspend fun PagingState<Int, CatalogEntity>.getRemoteKeyForFirstItem() =
        pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { entity -> localDatasource.getPaginationKeysForId(entity.id) }
}