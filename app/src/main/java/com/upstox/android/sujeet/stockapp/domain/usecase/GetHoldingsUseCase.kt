package com.upstox.android.sujeet.stockapp.domain.usecase

import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary
import com.upstox.android.sujeet.stockapp.domain.repository.HoldingsRepository
import com.upstox.android.sujeet.stockapp.utils.Result
import javax.inject.Inject

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
class GetHoldingsUseCase @Inject constructor(
    private val repository: HoldingsRepository
) {
    suspend operator fun invoke(): Result<PortfolioSummary> {
        return when (val result = repository.fetchHoldings()) {
            is Result.Success -> {
                val holdings = result.data
                val currentValue = holdings.sumOf { it.ltp * it.quantity }
                val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
                val totalPnL = currentValue - totalInvestment
                val todayPnL = holdings.sumOf { (it.close - it.ltp) * it.quantity }

                Result.Success(
                    PortfolioSummary(
                        holdings = holdings,
                        currentValue = currentValue,
                        totalInvestment = totalInvestment,
                        totalPnL = totalPnL,
                        todayPnL = todayPnL
                    )
                )
            }
            is Result.Error -> Result.Error(result.exception)
        }
    }
}