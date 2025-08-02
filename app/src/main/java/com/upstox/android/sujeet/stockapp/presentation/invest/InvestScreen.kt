package com.upstox.android.sujeet.stockapp.presentation.invest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.upstox.android.sujeet.stockapp.presentation.holdings.HoldingsViewModel

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
@Composable
fun InvestScreen(viewModel: HoldingsViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Invest Screen (Coming Soon)",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}