package com.upstox.android.sujeet.stockapp.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.upstox.android.sujeet.stockapp.R
import com.upstox.android.sujeet.stockapp.presentation.AppIntent

/**
 * Created by SUJEET KUMAR on 8/3/2025
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(
    title: String = stringResource(id = R.string.app_name),
    onEvent: (AppIntent) -> Unit,
) {

    CenterAlignedTopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {

            Button(onClick = {
//                    onEvent(AppIntent.BackClicked)
            }) {
                Icon(
                    painterResource(R.drawable.account_circle_24dp),
                    contentDescription = "Profile"
                )
            }

        },
        actions = {

            IconButton(
                onClick = {
//                            onEvent(AppIntent.ActionSettingClicked)
                }
            ) {
                Icon(
                    painterResource(R.drawable.sort_24dp),
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }


            // You can add more action icons here if needed

            IconButton(onClick = {
//                    onEvent(AppEvent.ActionRefreshClicked)
            }) {
                Icon(
                    painterResource(R.drawable.search_24dp),
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    )
}

@Preview
@Composable
fun AppBarTopPreview() {
    AppBarTop(
        title = "Portfolio",
        onEvent = {}
    )
}