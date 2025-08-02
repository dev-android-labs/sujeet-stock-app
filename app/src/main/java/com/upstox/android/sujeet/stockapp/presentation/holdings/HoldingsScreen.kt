package com.upstox.android.sujeet.stockapp.presentation.holdings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upstox.android.sujeet.stockapp.domain.model.Holding
import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary
import com.upstox.android.sujeet.stockapp.presentation.AppIntent
import com.upstox.android.sujeet.stockapp.presentation.components.SummaryBottomCard

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */


@Composable
fun HoldingsScreen(viewModel: HoldingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableStateOf(1) } // 0 = Positions, 1 = Holdings

    LaunchedEffect(Unit) {
        viewModel.onIntent(AppIntent.LoadHoldings)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // --- Top Tab Row ---
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab])
                        .height(3.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "POSITIONS",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "HOLDINGS",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            )
        }

        // --- Tab Content ---
        when (selectedTab) {
            0 -> PositionsPlaceholder()
            1 -> HoldingsTabContent(state, viewModel)
        }
    }
}

@Composable
fun PositionsPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Positions Coming Soon",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun HoldingsTabContent(
    state: HoldingsUiState,
    viewModel: HoldingsViewModel,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error != null -> Text(
                text = "Error: ${state.error}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )

            else -> HoldingsContent(
                state = state,
                onToggleSummary = { viewModel.onIntent(AppIntent.ToggleSummary) }
            )
        }
    }
}

@Composable
fun HoldingsContent(
    state: HoldingsUiState,
    onToggleSummary: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // --- Holdings List ---
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.holdings) { holding ->
                HoldingCard(holding)
            }
            item { Spacer(modifier = Modifier.height(100.dp)) } // Space for bottom card
        }

        // --- Bottom Expandable Summary ---
        SummaryBottomCard(
            summary = state.summary,
            isExpanded = state.isExpanded,
            onToggle = onToggleSummary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun HoldingCard(holding: Holding) {
    val pnl = (holding.ltp - holding.avgPrice) * holding.quantity
    val pnlColor = if (pnl >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    holding.symbol,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                Text("NET QTY: ${holding.quantity}", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "LTP: ₹${"%.2f".format(holding.ltp)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "P&L: ₹${"%.2f".format(pnl)}",
                    color = pnlColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview
@Composable
fun HoldingsScreenPreview() {
    val sampleHoldings = listOf(
        Holding(
            "AAPL", 10, 150.0, 145.0,
            close = 150.0
        ),
        Holding(
            "GOOGL", 5, 2800.0, 2700.0,
            close = 28500.0
        )
    )
    val sampleSummary = PortfolioSummary(
        sampleHoldings, 95000.0, 5000.0, 200.0,
        todayPnL = 150.0
    )

    HoldingsContent(
        state = HoldingsUiState(
            isLoading = false,
            holdings = sampleHoldings,
            summary = sampleSummary,
            isExpanded = true
        ),
        onToggleSummary = {}
    )
}
