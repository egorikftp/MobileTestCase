package com.egoriku.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egoriku.database.entity.CatalogEntity

@Dao
interface CatalogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CatalogEntity>)

    @Query("SELECT * FROM catalog")
    suspend fun getAll(): List<CatalogEntity>

    @Query("DELETE FROM catalog")
    suspend fun clearAll()

    @Query("SELECT * FROM catalog")
    fun pagingSource(): PagingSource<Int, CatalogEntity>
}