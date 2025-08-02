package com.upstox.android.sujeet.stockapp.presentation.holdings

import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
data class HoldingsUiState(
    val isLoading: Boolean = false,
    val holdings: List<Holding> = emptyList(),
    val summary: PortfolioSummary? = null,
    val error: String? = null,
    val isExpanded: Boolean = false
)