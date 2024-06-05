package com.wooyj.ordermenu.ui.screen.common.button.option

open class OptionGroupTitleUiState(
    val isVisible: Boolean = true,
    val title: Int,
    val optionGroupUiState: OptionGroupUiState,
) {
    data class OptionGroupUiState(
        val list: List<OptionButtonUiState>,
    )
}
