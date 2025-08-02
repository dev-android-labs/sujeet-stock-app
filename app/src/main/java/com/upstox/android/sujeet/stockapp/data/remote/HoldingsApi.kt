package com.upstox.android.sujeet.stockapp.data.remote

import com.upstox.android.sujeet.stockapp.data.remote.model.HoldingsResponse
import retrofit2.http.GET

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
interface HoldingsApi {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse
}