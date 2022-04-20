package com.egoriku.mobiletestcase.list.koin

import com.egoriku.mobiletestcase.list.data.datasource.CatalogLocalDatasource
import com.egoriku.mobiletestcase.list.data.datasource.CatalogNetworkDatasource
import com.egoriku.mobiletestcase.list.data.repository.CatalogRepository
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogLocalDatasource
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogNetworkDatasource
import com.egoriku.mobiletestcase.list.domain.repository.ICatalogRepository
import com.egoriku.mobiletestcase.list.domain.usecase.CatalogUseCase
import com.egoriku.mobiletestcase.list.domain.usecase.ICatalogUseCase
import com.egoriku.mobiletestcase.list.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    factory<ICatalogNetworkDatasource> {
        CatalogNetworkDatasource(apiService = get())
    }
    factory<ICatalogLocalDatasource> {
        CatalogLocalDatasource(database = get())
    }
    factory<ICatalogRepository> {
        CatalogRepository(
            networkDatasource = get(),
            localDatasource = get()
        )
    }
    factory<ICatalogUseCase> {
        CatalogUseCase(catalogRepository = get())
    }
    viewModel {
        ListViewModel(catalogUseCase = get())
    }
}