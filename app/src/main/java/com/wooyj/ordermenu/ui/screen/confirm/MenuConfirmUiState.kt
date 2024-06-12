package com.wooyj.ordermenu.ui.screen.confirm

import com.wooyj.ordermenu.data.dto.OrderOption

sealed class MenuConfirmUiState {
    data object None : MenuConfirmUiState()

    data object Loading : MenuConfirmUiState()

    data class Success(
        val orderOption: OrderOption,
    ) : MenuConfirmUiState()

    data class Error(
        val exception: Throwable,
    ) : MenuConfirmUiState()
}
