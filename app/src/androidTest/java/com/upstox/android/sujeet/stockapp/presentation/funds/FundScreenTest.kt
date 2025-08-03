package com.upstox.android.sujeet.stockapp.presentation.funds


import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.upstox.android.sujeet.stockapp.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
class FundScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun fundScreen_showsComingSoonText() {
        composeTestRule.setContent {
           FundScreen()
        }
        composeTestRule.onNodeWithText("Fund Screen (Coming Soon)").assertIsDisplayed()
    }
}