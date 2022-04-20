package com.egoriku.mobiletestcase.list.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.egoriku.mobiletestcase.list.domain.mapper.CatalogEntityToModelMapper
import com.egoriku.mobiletestcase.list.domain.repository.ICatalogRepository
import kotlinx.coroutines.flow.map

private const val ITEMS_PER_PAGE = 10

internal class CatalogUseCase(private val catalogRepository: ICatalogRepository) : ICatalogUseCase {

    @OptIn(ExperimentalPagingApi::class)
    override fun catalogItems() = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        remoteMediator = catalogRepository.remoteMediator(),
        pagingSourceFactory = {
            catalogRepository.pagingSource()
        }
    ).flow.map { pagingData ->
        pagingData.map(transform = CatalogEntityToModelMapper())
    }
}