package com.upstox.android.sujeet.stockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.upstox.android.sujeet.stockapp.presentation.components.AppBarTop
import com.upstox.android.sujeet.stockapp.presentation.routes.AppNavGraph
import com.upstox.android.sujeet.stockapp.presentation.routes.BottomNavBar
import com.upstox.android.sujeet.stockapp.ui.theme.SujeetStockAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val appTitle = remember { mutableStateOf("Stock App") }

            SujeetStockAppTheme {
                Scaffold(
                    topBar = {
                        AppBarTop(
                            title = appTitle.value,
                            onEvent = { /* Handle app events here */ }
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(navController) { title ->
                            appTitle.value = title
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SujeetStockAppTheme {
        Text(text = "Hello, World!", modifier = Modifier.fillMaxSize())
    }
}