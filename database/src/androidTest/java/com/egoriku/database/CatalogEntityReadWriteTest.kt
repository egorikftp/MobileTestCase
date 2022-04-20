package com.egoriku.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.egoriku.database.dao.CatalogDao
import com.egoriku.database.entity.CatalogEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.properties.Delegates

@RunWith(AndroidJUnit4::class)
class CatalogEntityReadWriteTest {

    private var catalogDao: CatalogDao by Delegates.notNull()
    private var db: CatalogDatabase by Delegates.notNull()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            CatalogDatabase::class.java
        ).build()

        catalogDao = db.catalogDao()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertCatalogItemsAndGet() = runTest {
        val itemsSize = 10

        val items = List(itemsSize) {
            CatalogEntity(
                text = "$it",
                confidence = it.toDouble(),
                image = "",
                id = "${it * 1000}"
            )
        }

        catalogDao.insertAll(items = items)

        val catalogs = catalogDao.getAll()

        assertEquals(itemsSize, catalogs.size)
        assertEquals(items.first().id, catalogs.first().id)
    }

    @After
    fun closeDb() {
        db.close()
    }
}