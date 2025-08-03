package com.upstox.android.sujeet.stockapp.data.repository

import com.upstox.android.sujeet.stockapp.data.local.dao.HoldingsDao
import com.upstox.android.sujeet.stockapp.data.local.entity.toEntity
import com.upstox.android.sujeet.stockapp.data.remote.HoldingsApi
import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.domain.repository.HoldingsRepository
import com.upstox.android.sujeet.stockapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
class HoldingsRepositoryImpl(
    private val api: HoldingsApi,
    private val dao: HoldingsDao,
) : HoldingsRepository {

    private val TAG: String = "HoldingsRepositoryImpl"

    override suspend fun fetchHoldings(): Result<List<Holding>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getHoldings()

            val holdings = response.data?.userHolding?.map {
                Holding(it.symbol, it.quantity, it.ltp, it.avgPrice, it.close)
            }
            // Cache to Room
            dao.insertAll(holdings?.map { it.toEntity() } ?: emptyList())
            Result.Success(holdings)
        } catch (e: Exception) {
            val cached = dao.getAll().map { it.toDomain() }
            if (cached.isNotEmpty()) Result.Success(cached)
            else Result.Error(e)
        } as Result<List<Holding>>
    }
}