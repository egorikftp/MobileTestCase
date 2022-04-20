package com.egoriku.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egoriku.database.dao.CatalogDao
import com.egoriku.database.dao.PaginationKeysDao
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.database.entity.PaginationKeysEntity

@Database(entities = [CatalogEntity::class, PaginationKeysEntity::class], version = 1)
abstract class CatalogDatabase : RoomDatabase() {

    abstract fun catalogDao(): CatalogDao

    abstract fun paginationKeysDao(): PaginationKeysDao
}