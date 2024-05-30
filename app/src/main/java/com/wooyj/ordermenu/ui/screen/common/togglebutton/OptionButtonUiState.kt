package com.wooyj.ordermenu.ui.screen.common.togglebutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO("uiState용 class는 무조건 data class여야 하나요? (toggle button쪽 코드는... 제가 잘 쓴건지 전혀 모르겠습니다..ㅠ)")
// data class ToggleButtonGroupWithTitleUiState(
//    val isVisible : Boolean = true,
//    val title: String,
//    val toggleButtonGroup: ToggleButtonGroupUiState,
// )
open class OptionGroupUiState(
    val isVisible: Boolean = true,
    val title: String,
    val optionButtonGroupUiState: OptionButtonGroupUiState,
) {
    data class OptionButtonGroupUiState(
        val selectedOption: OptionButtonUiState,
        val list: List<OptionButtonUiState>,
    )
}

data class OptionButtonUiState(
    val optionName: String,
    var isSelected: Boolean = false,
)

@Composable
fun OptionButtonUiState.containerColor(): Color {
    return if (isSelected) {
        Color.DarkGray
    } else {
        Color.LightGray
    }
}
