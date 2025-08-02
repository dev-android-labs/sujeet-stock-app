package com.upstox.android.sujeet.stockapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upstox.android.sujeet.stockapp.domain.model.Holding

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
@Entity(tableName = "holdings")
data class HoldingEntity(
    @PrimaryKey val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
) {
    fun toDomain(): Holding = Holding(symbol, quantity, ltp, avgPrice, close)
}

fun Holding.toEntity() = HoldingEntity(symbol, quantity, ltp, avgPrice, close)