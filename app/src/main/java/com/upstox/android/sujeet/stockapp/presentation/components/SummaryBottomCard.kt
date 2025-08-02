package com.upstox.android.sujeet.stockapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upstox.android.sujeet.stockapp.domain.model.PortfolioSummary

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
@Composable
fun SummaryBottomCard(
    summary: PortfolioSummary?,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated Arrow
            val rotation by animateFloatAsState(if (isExpanded) 180f else 0f)
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Expand",
                modifier = Modifier.rotate(rotation)
            )

            // Always show total PnL
            summary?.let {
                val pnlColor = if (it.totalPnL >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                Text(
                    text = "Profit & Loss: ₹${"%.2f".format(it.totalPnL)}",
                    color = pnlColor,
                    fontWeight = FontWeight.Bold
                )
            }

            // Slide Animation for Expanded Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                summary?.let {
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Current Value:")
                            Text("₹${"%.2f".format(it.currentValue)}")
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Investment:")
                            Text("₹${"%.2f".format(it.totalInvestment)}")
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Today's P&L:")
                            Text(
                                "₹${"%.2f".format(it.todayPnL)}",
                                color = if (it.todayPnL >= 0) Color(0xFF4CAF50) else Color(
                                    0xFFF44336
                                )
                            )
                        }

                    }
                }
            }
        }
    }
}