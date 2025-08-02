package com.upstox.android.sujeet.stockapp.presentation

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
sealed class AppIntent {
    object LoadHoldings : AppIntent()
    object ToggleSummary : AppIntent()
}