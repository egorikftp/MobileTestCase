package com.egoriku.mobiletestcase.list.data.repository

import android.app.Application
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.egoriku.database.CatalogDatabase
import com.egoriku.database.entity.CatalogEntity
import com.egoriku.database.entity.PaginationKeysEntity
import com.egoriku.mobiletestcase.list.data.datasource.CatalogLocalDatasource
import com.egoriku.mobiletestcase.list.domain.datasourse.ICatalogNetworkDatasource
import com.egoriku.network.response.CatalogResponse
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

inline fun <reified T : Any> mockk() = lazyOf(mock<T>())

@RunWith(AndroidJUnit4::class)
@Config(sdk = [29], application = Application::class)
@OptIn(ExperimentalPagingApi::class)
class CatalogRemoteMediatorTest {

    private val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        CatalogDatabase::class.java
    ).allowMainThreadQueries()
        .build()

    private val networkDatasource: ICatalogNetworkDatasource by mockk()
    private val localDatasource = CatalogLocalDatasource(database = db)

    private val remoteMediator = CatalogRemoteMediator(
        networkDatasource = networkDatasource,
        localDatasource = localDatasource
    )

    @Test
    fun testRefreshStateSuccess() = runTest {
        whenever(networkDatasource.loadInitial()).thenReturn(mockResponse(count = 10, from = 0))
        whenever(networkDatasource.loadAppend(any())).thenThrow(IllegalStateException())
        whenever(networkDatasource.loadPrepend(any())).thenThrow(IllegalStateException())

        val pagingState = PagingState<Int, CatalogEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        assertTrue(db.catalogDao().getAll().size == 10)
        assertTrue(db.paginationKeysDao().getAll().size == 10)
    }

    @Test
    fun testRefreshStateError() = runTest {
        whenever(networkDatasource.loadInitial()).thenReturn(emptyList())
        whenever(networkDatasource.loadAppend(any())).thenThrow(IllegalStateException())
        whenever(networkDatasource.loadPrepend(any())).thenThrow(IllegalStateException())

        val pagingState = PagingState<Int, CatalogEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        assertTrue(db.catalogDao().getAll().isEmpty())
        assertTrue(db.paginationKeysDao().getAll().isEmpty())
    }

    @Test
    fun testAppendStateSuccess() = runTest {
        val prevKey = 100
        val nextKey = 200

        db.paginationKeysDao().insertAll(
            mockPaginationKeys(count = 10, prevKey = prevKey, nextKey = nextKey)
        )

        whenever(networkDatasource.loadAppend(key = "$nextKey")).thenReturn(
            mockResponse(
                count = 10,
                from = 11
            )
        )
        whenever(networkDatasource.loadInitial()).thenThrow(IllegalStateException())
        whenever(networkDatasource.loadPrepend(any())).thenThrow(IllegalStateException())

        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = mockEntity(10, 1),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        assertEquals(10, db.catalogDao().getAll().size)
        assertEquals(20, db.paginationKeysDao().getAll().size)
    }

    @Test
    fun testAppendStateError() = runTest {
        val prevKey = 100
        val nextKey = 200
        val initiaPaginationSize = 10

        db.paginationKeysDao().insertAll(
            mockPaginationKeys(count = initiaPaginationSize, prevKey = prevKey, nextKey = nextKey)
        )

        whenever(networkDatasource.loadAppend(key = "$nextKey")).thenReturn(
            mockResponse(
                count = 10,
                from = 11
            )
        )

        whenever(networkDatasource.loadAppend(any())).thenReturn(emptyList())
        whenever(networkDatasource.loadInitial()).thenThrow(IllegalStateException())
        whenever(networkDatasource.loadPrepend(any())).thenThrow(IllegalStateException())

        val pagingState = PagingState<Int, CatalogEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        assertTrue(db.catalogDao().getAll().isEmpty())
        assertEquals(initiaPaginationSize, db.paginationKeysDao().getAll().size)
    }


    @After
    fun tearDown() {
        db.clearAllTables()
    }

    private fun mockResponse(count: Int, from: Int) =
        List(count) {
            CatalogResponse(
                id = "${it + from}",
                text = "",
                confidence = 0.0,
                image = ""
            )
        }

    private fun mockEntity(count: Int, from: Int) =
        List(count) {
            CatalogEntity(
                id = "${it + from}",
                text = "",
                confidence = 0.0,
                image = ""
            )
        }


    private fun mockPaginationKeys(
        count: Int,
        prevKey: Int,
        nextKey: Int
    ) = List(count) {
        PaginationKeysEntity(
            id = "${it + 1}",
            prevKey = prevKey.toString(),
            nextKey = nextKey.toString()
        )
    }
}