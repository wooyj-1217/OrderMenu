package com.wooyj.ordermenu.ui.screen.common.button

import com.wooyj.ordermenu.R

sealed class BottomButtonUiState {
    data object Close : BottomButtonUiState()

    data object Next : BottomButtonUiState()
}

fun BottomButtonUiState.text(): Int =
    when (this) {
        BottomButtonUiState.Close -> R.string.close
        BottomButtonUiState.Next -> R.string.next
    }
