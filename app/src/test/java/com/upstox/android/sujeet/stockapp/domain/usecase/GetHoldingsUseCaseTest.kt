package com.upstox.android.sujeet.stockapp.domain.usecase


/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary
import com.upstox.android.sujeet.stockapp.domain.repository.HoldingsRepository
import com.upstox.android.sujeet.stockapp.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetHoldingsUseCaseTest {

    private lateinit var useCase: GetHoldingsUseCase
    private val repository: HoldingsRepository = mockk()

    @Before
    fun setup() {
        useCase = GetHoldingsUseCase(repository)
    }

    @Test
    fun `when repository returns data, should calculate correct PortfolioSummary`() = runTest {
        // Given
        val holdings = listOf(
            Holding(symbol = "HDFC", quantity = 10, ltp = 2500.0, avgPrice = 2000.0, close = 2480.0),
            Holding(symbol = "ASHOKLEY", quantity = 5, ltp = 120.0, avgPrice = 100.0, close = 119.0)
        )

        coEvery { repository.fetchHoldings() } returns Result.Success(holdings)

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        val summary = (result as Result.Success<PortfolioSummary>).data

        val expectedCurrentValue = 10 * 2500.0 + 5 * 120.0
        val expectedTotalInvestment = 10 * 2000.0 + 5 * 100.0
        val expectedTotalPnL = expectedCurrentValue - expectedTotalInvestment
        val expectedTodayPnL = (2480.0 - 2500.0) * 10 + (119.0 - 120.0) * 5

        assertEquals(expectedCurrentValue, summary.currentValue, 0.01)
        assertEquals(expectedTotalInvestment, summary.totalInvestment, 0.01)
        assertEquals(expectedTotalPnL, summary.totalPnL, 0.01)
        assertEquals(expectedTodayPnL, summary.todayPnL, 0.01)
    }

    @Test
    fun `when repository returns error, should return Result Error`() = runTest {
        // Given
        coEvery { repository.fetchHoldings() } returns Result.Error(Exception("Network error"))

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals("Network error", (result as Result.Error).exception.message)
    }
}
