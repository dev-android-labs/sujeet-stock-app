package com.upstox.android.sujeet.stockapp.data.repository

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
import com.upstox.android.sujeet.stockapp.data.local.dao.HoldingsDao
import com.upstox.android.sujeet.stockapp.data.local.entity.HoldingEntity
import com.upstox.android.sujeet.stockapp.data.remote.model.HoldingDto
import com.upstox.android.sujeet.stockapp.data.remote.HoldingsApi
import com.upstox.android.sujeet.stockapp.data.remote.model.Data
import com.upstox.android.sujeet.stockapp.data.remote.model.HoldingsResponse
import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsRepositoryImplTest {

    private val api: HoldingsApi = mockk()
    private val dao: HoldingsDao = mockk(relaxed = true) // relaxed to auto-stub Unit
    private lateinit var repository: HoldingsRepositoryImpl

    @Before
    fun setup() {
        repository = HoldingsRepositoryImpl(api, dao)
    }

    @Test
    fun `when API returns success, repository saves to DB and returns Success`() = runTest {
        // Given
        val apiHoldings = Data(listOf(
            HoldingDto("HDFC", 10, 2500.0, 2000.0, 2480.0),
            HoldingDto("ASHOKLEY", 5, 120.0, 100.0, 119.0))
        )
        coEvery { api.getHoldings() } returns HoldingsResponse(apiHoldings)

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertEquals(2, data.size)
        assertEquals("HDFC", data.first().symbol)

        // Verify DB insert called
        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `when API fails and DB has cache, repository returns Success from cache`() = runTest {
        // Given
        coEvery { api.getHoldings() } throws RuntimeException("Network error")
        coEvery { dao.getAll() } returns listOf(
            HoldingEntity("HDFC", 10, 2500.0, 2000.0, 2480.0)
        )

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertEquals(1, data.size)
        assertEquals("HDFC", data.first().symbol)
    }

    @Test
    fun `when API fails and DB is empty, repository returns Error`() = runTest {
        // Given
        coEvery { api.getHoldings() } throws RuntimeException("Network error")
        coEvery { dao.getAll() } returns emptyList()

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Error)
        val exception = (result as Result.Error).exception
        assertEquals("Network error", exception.message)
    }

    @Test
    fun `when API returns empty list, repository returns empty Success`() = runTest {
        // Given
        coEvery { api.getHoldings() } returns HoldingsResponse(Data(emptyList()))

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertTrue(data.isEmpty())
        coVerify { dao.insertAll(emptyList()) }
    }

    @Test
    fun `when API returns negative quantity, repository returns same data`() = runTest {
        // Given
        val malformedData = Data(listOf(HoldingDto("TEST", -5, 100.0, 90.0, 95.0)))
        coEvery { api.getHoldings() } returns HoldingsResponse(malformedData)

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertEquals(-5, data.first().quantity)
    }

    @Test
    fun `when API returns partial data and DB has old cache, new data overwrites DB`() = runTest {
        // Given
        val oldCache = listOf(HoldingEntity("OLD", 10, 200.0, 180.0, 190.0))
        val newData = Data(listOf(HoldingDto("NEW", 5, 100.0, 90.0, 95.0)))

        coEvery { api.getHoldings() } returns HoldingsResponse(newData)
        coEvery { dao.getAll() } returns oldCache

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertEquals(1, data.size)
        assertEquals("NEW", data.first().symbol)

        // Verify DB insert replaces old cache
        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `when API is slow and throws timeout, fallback to DB`() = runTest {
        // Given
        val cachedData = listOf(HoldingEntity("CACHE", 2, 100.0, 95.0, 98.0))
        coEvery { dao.getAll() } returns cachedData

        coEvery { api.getHoldings() } coAnswers {
            delay(100) // simulate slow network
            throw RuntimeException("Timeout")
        }

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertEquals("CACHE", data.first().symbol)
    }

    @Test
    fun `when API returns null data, repository returns empty Success`() = runTest {
        // Given
        coEvery { api.getHoldings() } returns HoldingsResponse(data = null)

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertTrue(data.isNullOrEmpty())
        coVerify { dao.insertAll(emptyList()) }
    }

    @Test
    fun `when API returns null userHolding, repository returns empty Success`() = runTest {
        // Given
        coEvery { api.getHoldings() } returns HoldingsResponse(Data(userHolding = emptyList()))

        // When
        val result = repository.fetchHoldings()

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success<List<Holding>>).data
        assertTrue(data.isEmpty())
        coVerify { dao.insertAll(emptyList()) }
    }
}