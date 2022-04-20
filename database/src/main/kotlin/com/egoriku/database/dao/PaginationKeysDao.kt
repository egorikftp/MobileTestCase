package com.egoriku.database.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egoriku.database.entity.PaginationKeysEntity

@Dao
interface PaginationKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PaginationKeysEntity>)

    @Query("DELETE FROM pagination_keys")
    suspend fun clearAll()

    @Query("SELECT * FROM pagination_keys WHERE id = :id")
    suspend fun getPaginationKeysForId(id: String): PaginationKeysEntity?

    @VisibleForTesting
    @Query("SELECT * FROM pagination_keys")
    suspend fun getAll(): List<PaginationKeysEntity>
}