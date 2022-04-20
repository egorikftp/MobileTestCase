package com.egoriku.mobiletestcase.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.egoriku.mobiletestcase.list.domain.usecase.ICatalogUseCase

class ListViewModel(catalogUseCase: ICatalogUseCase) : ViewModel() {

    var isFirstLoad = true

    val pagingData = catalogUseCase
        .catalogItems()
        .cachedIn(viewModelScope)
}