package com.egoriku.mobiletestcase.list.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogModel(
    val id: String,
    val imageUrl: String,
    val confidence: String,
    val description: String
) : Parcelable