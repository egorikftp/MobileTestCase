package com.egoriku.mobiletestcase.list.domain.usecase

import androidx.paging.PagingData
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel
import kotlinx.coroutines.flow.Flow

interface ICatalogUseCase {

    fun catalogItems(): Flow<PagingData<CatalogModel>>
}