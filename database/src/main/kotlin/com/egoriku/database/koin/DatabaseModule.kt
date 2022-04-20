package com.egoriku.database.koin

import androidx.room.Room
import com.egoriku.database.CatalogDatabase
import org.koin.dsl.module

private const val DB_NAME = "catalog_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CatalogDatabase::class.java,
            DB_NAME
        ).build()
    }
}