package com.wooyj.ordermenu.ui.screen.common.button.option

import androidx.compose.ui.graphics.Color

data class OptionButtonUiState(
    val optionName: String,
    var isSelected: Boolean = false,
)

fun OptionButtonUiState.containerColor() = if (isSelected) Color.DarkGray else Color.LightGray