package com.egoriku.mobiletestcase.list.data.mapper

import com.egoriku.database.entity.CatalogEntity
import com.egoriku.mobiletestcase.util.IMapper
import com.egoriku.network.response.CatalogResponse

internal class CatalogResponseToEntityMapper : IMapper<CatalogResponse, CatalogEntity> {

    override fun invoke(response: CatalogResponse): CatalogEntity {
        return CatalogEntity(
            id = response.id,
            image = response.image,
            confidence = response.confidence,
            text = response.text
        )
    }
}