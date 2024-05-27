package com.wooyj.ordermenu.ui.screen.common.uistate

sealed class UiState<out T> {
    data object None : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val exception: Throwable) : UiState<Nothing>()
}
