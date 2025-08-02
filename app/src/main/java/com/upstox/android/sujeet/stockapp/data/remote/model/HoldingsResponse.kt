package com.upstox.android.sujeet.stockapp.data.remote.model

import com.squareup.moshi.Json

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */

data class HoldingsResponse(
    @Json(name = "data") var data : Data? = Data()
)

data class Data (
    @Json(name = "userHolding") var userHolding : List<HoldingDto> = arrayListOf()

)

data class HoldingDto(
    @Json(name = "symbol") val symbol: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "ltp") val ltp: Double,
    @Json(name = "avgPrice") val avgPrice: Double,
    @Json(name = "close") val close: Double
)
