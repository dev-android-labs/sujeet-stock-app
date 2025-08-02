package com.upstox.android.sujeet.stockapp.presentation.routes

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.upstox.android.sujeet.stockapp.presentation.funds.FundScreen
import com.upstox.android.sujeet.stockapp.presentation.holdings.HoldingsScreen
import com.upstox.android.sujeet.stockapp.presentation.invest.InvestScreen
import com.upstox.android.sujeet.stockapp.presentation.orders.OrderScreen
import com.upstox.android.sujeet.stockapp.presentation.watchlist.WatchlistScreen

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
@Composable
fun AppNavGraph(navController: NavHostController, onNavItemClick: (String) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Portfolio.route
    ) {
        composable(BottomNavItem.Watchlist.route) {
            WatchlistScreen()
            onNavItemClick(BottomNavItem.Watchlist.label)
        }
        composable(BottomNavItem.Orders.route) {
            OrderScreen()
            onNavItemClick(BottomNavItem.Orders.label)
        }
        composable(BottomNavItem.Portfolio.route) {
            HoldingsScreen()
            onNavItemClick(BottomNavItem.Portfolio.label)
        }
        composable(BottomNavItem.Funds.route) {
            FundScreen()
            onNavItemClick(BottomNavItem.Funds.label)
        }
        composable(BottomNavItem.Invest.route) {
            InvestScreen()
            onNavItemClick(BottomNavItem.Invest.label)
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Watchlist,
        BottomNavItem.Orders,
        BottomNavItem.Portfolio,
        BottomNavItem.Funds,
        BottomNavItem.Invest
    )

    NavigationBar(containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}


@Preview
@Composable
fun PreviewAppNavGraph() {
    AppNavGraph(
        navController = rememberNavController(),
        onNavItemClick = { /* Handle navigation item click */ }
    )
}