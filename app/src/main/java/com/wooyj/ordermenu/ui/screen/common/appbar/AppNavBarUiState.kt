package com.wooyj.ordermenu.ui.screen.common.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppNavBarUiState {
    data object Intro : AppNavBarUiState()

    data object List : AppNavBarUiState()

    data object Option : AppNavBarUiState()

    data object Confirm : AppNavBarUiState()
}

fun AppNavBarUiState.isDisplay(): Boolean = this !is AppNavBarUiState.Intro

fun AppNavBarUiState.icon(): ImageVector =
    when (this) {
        AppNavBarUiState.Intro -> throw IllegalStateException("Intro screen does not have a navigation icon")
        else -> Icons.Default.ArrowBack
    }

fun AppNavBarUiState.title(): String =
    when (this) {
        AppNavBarUiState.Intro -> ""
        AppNavBarUiState.List -> ""
        AppNavBarUiState.Option -> ""
        AppNavBarUiState.Confirm -> ""
    }
