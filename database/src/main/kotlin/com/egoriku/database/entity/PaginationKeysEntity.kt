package com.egoriku.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagination_keys")
data class PaginationKeysEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "prevKey")
    val prevKey: String?,

    @ColumnInfo(name = "nextKey")
    val nextKey: String?
)