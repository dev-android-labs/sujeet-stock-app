package com.upstox.android.sujeet.stockapp.domain.repository

import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.utils.Result

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
interface HoldingsRepository {
    suspend fun fetchHoldings(): Result<List<Holding>>
}