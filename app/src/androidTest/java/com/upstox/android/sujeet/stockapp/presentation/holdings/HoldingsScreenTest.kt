package com.upstox.android.sujeet.stockapp.presentation.holdings


import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.upstox.android.sujeet.stockapp.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */

class HoldingsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun holdingsScreen_showsTabsAndPositionsPlaceholder() {
        composeTestRule.setContent {
            HoldingsScreen()
        }
        composeTestRule.onNodeWithText("POSITIONS").assertIsDisplayed()
        composeTestRule.onNodeWithText("HOLDINGS").assertIsDisplayed()
        composeTestRule.onNodeWithText("Positions Coming Soon").assertIsDisplayed()
    }
}