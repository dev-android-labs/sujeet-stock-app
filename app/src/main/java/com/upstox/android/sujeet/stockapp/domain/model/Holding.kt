package com.upstox.android.sujeet.stockapp.domain.model

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)