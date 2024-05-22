package com.wooyj.ordermenu.ui.screen.common.button

import androidx.compose.runtime.Composable

sealed class BottomButtonUiState {
    data object Next : BottomButtonUiState()

    data object Close : BottomButtonUiState()
}

@Composable
fun BottomButtonUiState.text(): String {
    return when (this) {
        BottomButtonUiState.Next -> "다음"
        BottomButtonUiState.Close -> "닫기"
    }
}
