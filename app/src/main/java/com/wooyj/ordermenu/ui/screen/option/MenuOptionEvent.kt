package com.wooyj.ordermenu.ui.screen.option

import com.wooyj.ordermenu.ui.screen.common.button.option.OptionButtonUiState

sealed class MenuOptionEvent {
    data class ClickTempOption(
        val tempOption: OptionButtonUiState,
    ) : MenuOptionEvent()

    data class ClickCaffeineOption(
        val caffeineOption: OptionButtonUiState,
    ) : MenuOptionEvent()

    data class ClickIceOption(
        val iceOption: OptionButtonUiState,
    ) : MenuOptionEvent()
}
