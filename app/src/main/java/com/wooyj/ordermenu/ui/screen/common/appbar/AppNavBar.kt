package com.wooyj.ordermenu.ui.screen.common.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavBar(
    uiState: AppNavBarUiState,
    modifier: Modifier = Modifier,
    navAction: () -> Unit,
) {
    if (uiState.isDisplay()) {
        TopAppBar(
            title = { Text(text = uiState.title()) },
            navigationIcon = {
                IconButton(
                    onClick = navAction,
                ) {
                    Icon(
                        imageVector = uiState.icon(),
                        contentDescription = "",
                    )
                }
            },
        )
    }
}
