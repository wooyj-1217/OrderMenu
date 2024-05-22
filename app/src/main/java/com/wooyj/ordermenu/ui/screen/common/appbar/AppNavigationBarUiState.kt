package com.wooyj.ordermenu.ui.screen.common.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.wooyj.ordermenu.R

sealed class AppNavigationBarUiState {
    data object Intro : AppNavigationBarUiState()

    data object List : AppNavigationBarUiState()

    data object Option : AppNavigationBarUiState()

    data object Confirm : AppNavigationBarUiState()
}

/**
 *
 */
@Composable
fun AppNavigationBarUiState.isDisplay(): Boolean {
    return this !is AppNavigationBarUiState.Intro
}

/**
 *
 */
@Composable
fun AppNavigationBarUiState.title(): String {
    return when (this) {
        AppNavigationBarUiState.Intro -> throw IllegalStateException("Intro does not have title")
        AppNavigationBarUiState.List -> stringResource(R.string.app_name)
        AppNavigationBarUiState.Option -> stringResource(R.string.app_name)
        AppNavigationBarUiState.Confirm -> stringResource(R.string.app_name)
    }
}

/**
 *
 */
@Composable
fun AppNavigationBarUiState.icon(): ImageVector {
    return when (this) {
        AppNavigationBarUiState.Intro -> throw IllegalStateException("Intro does not have icon")
        else -> Icons.Default.ArrowBack
    }
}
