package com.wooyj.ordermenu.ui.intro

sealed class IntroUiState {
    data object None : IntroUiState()

    data class Intro(val text: String) : IntroUiState()

    data class Loading(val text: String) : IntroUiState()

    data class Error(val text: String) : IntroUiState()
}
