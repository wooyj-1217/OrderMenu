package com.wooyj.ordermenu.ui.screen.common.uistate

// TODO("해당 uiState(none, loading, success, error)를 작성할 때 각 스크린 별로 작성하는 것의 이점에 어떤게 있을까요?")
sealed class UiState<out T> {
    data object None : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val exception: Throwable) : UiState<Nothing>()
}
