package com.wooyj.ordermenu.ui.screen.common.uistate

sealed class UiState<out T> {
    // TODO("data object와 object의 차이, data object를 써야하는 이유?")
    data object None : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val exception: Throwable) : UiState<Nothing>()
}
