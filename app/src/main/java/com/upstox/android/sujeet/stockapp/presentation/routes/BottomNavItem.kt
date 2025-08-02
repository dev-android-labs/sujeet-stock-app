package com.upstox.android.sujeet.stockapp.presentation.routes

import com.upstox.android.sujeet.stockapp.R

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {
    object Watchlist : BottomNavItem("watchlist", "Watchlist", R.drawable.watchlist_24dp)
    object Orders : BottomNavItem("orders", "Orders", R.drawable.orders_24dp)
    object Portfolio : BottomNavItem("portfolio", "Portfolio", R.drawable.portfolio_24dp)
    object Funds : BottomNavItem("funds", "Funds", R.drawable.funds_24dp)
    object Invest : BottomNavItem("Invest", "Invest", R.drawable.invest_24dp)
}



