package com.upstox.android.sujeet.stockapp.domain.model

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
data class PortfolioSummary(
    val holdings: List<Holding>,
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnL: Double,
    val todayPnL: Double
)