package com.wooyj.ordermenu.ui.screen.intro

sealed class IntroUiState {
    data object None : IntroUiState()

    data object Loading : IntroUiState()

    data class Success(val text: String) : IntroUiState()

    data class Error(val exception: Throwable) : IntroUiState()
}
