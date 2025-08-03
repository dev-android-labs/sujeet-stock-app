package com.upstox.android.sujeet.stockapp.presentation.holdings

import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary
import com.upstox.android.sujeet.stockapp.domain.usecase.GetHoldingsUseCase
import com.upstox.android.sujeet.stockapp.presentation.AppIntent
import com.upstox.android.sujeet.stockapp.utils.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    private lateinit var getHoldingsUseCase: GetHoldingsUseCase
    private lateinit var viewModel: HoldingsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getHoldingsUseCase = mock(GetHoldingsUseCase::class.java)
        viewModel = HoldingsViewModel(getHoldingsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadHoldings success updates state with holdings and summary`() = runTest {
        val holdingsData = mockk<PortfolioSummary>()
        val expectedHoldings = listOf(mockk<Holding>())
        every { holdingsData.holdings } returns expectedHoldings

        whenever(getHoldingsUseCase()).thenReturn(Result.Success(holdingsData))

        viewModel.onIntent(AppIntent.LoadHoldings)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(expectedHoldings, state.holdings)
        assertEquals(holdingsData, state.summary)
        assertNull(state.error)
    }

    @Test
    fun `loadHoldings error updates state with error message`() = runTest {
        whenever(getHoldingsUseCase()).thenReturn(Result.Error(Exception("Network error")))

        viewModel.onIntent(AppIntent.LoadHoldings)

        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutine completes

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Network error", state.error)
    }

    @Test
    fun `toggleSummary toggles isExpanded`() {
        val initial = viewModel.state.value.isExpanded
        viewModel.onIntent(AppIntent.ToggleSummary)
        assertEquals(!initial, viewModel.state.value.isExpanded)
    }

    @Test
    fun `toggleSummary toggles isExpanded repeatedly`() {
        val initial = viewModel.state.value.isExpanded
        // Toggle once
        viewModel.onIntent(AppIntent.ToggleSummary)
        assertEquals(!initial, viewModel.state.value.isExpanded)
        // Toggle again
        viewModel.onIntent(AppIntent.ToggleSummary)
        assertEquals(initial, viewModel.state.value.isExpanded)
    }
}