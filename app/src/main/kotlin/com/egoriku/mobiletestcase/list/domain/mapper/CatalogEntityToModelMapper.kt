package com.egoriku.mobiletestcase.list.domain.mapper

import com.egoriku.database.entity.CatalogEntity
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel
import com.egoriku.mobiletestcase.util.IMapper

class CatalogEntityToModelMapper : IMapper<CatalogEntity, CatalogModel> {

    override fun invoke(entity: CatalogEntity): CatalogModel {
        return CatalogModel(
            id = entity.id,
            imageUrl = entity.image,
            confidence = entity.confidence.toString(),
            description = entity.text
        )
    }
}