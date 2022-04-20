package com.egoriku.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponse(
    @SerialName("_id")
    val id: String,

    @SerialName("text")
    val text: String,

    @SerialName("confidence")
    val confidence: Double,

    @SerialName("image")
    val image: String
)