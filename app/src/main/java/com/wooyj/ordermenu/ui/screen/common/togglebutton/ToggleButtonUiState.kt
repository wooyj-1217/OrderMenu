package com.wooyj.ordermenu.ui.screen.common.togglebutton

data class ToggleButtonUiState(
    val optionName: String,
    var isSelected: Boolean = false,
)

data class ToggleButtonGroupUiState(
    val list: List<ToggleButtonUiState>,
) {
    fun selectOption(optionName: String): ToggleButtonGroupUiState {
        val newList =
            list.map {
                it.copy(isSelected = it.optionName == optionName)
            }
        return this.copy(list = newList)
    }
}

// TODO("uiState용 class는 무조건 data class여야 하나요? (toggle button쪽 코드는... 제가 잘 쓴건지 전혀 모르겠습니다..ㅠ)")
// data class ToggleButtonGroupWithTitleUiState(
//    val isVisible : Boolean = true,
//    val title: String,
//    val toggleButtonGroup: ToggleButtonGroupUiState,
// )
sealed class ToggleButtonGroupWithTitleUiState(
    open val isVisible: Boolean = true,
    open val title: String,
    open val toggleButtonGroup: ToggleButtonGroupUiState,
)

data class TempOptionToggleButtonGroupUiState(
    override val isVisible: Boolean,
//    override val title: String = stringResource(R.string.basic),
    override val title: String = "기본",
    override val toggleButtonGroup: ToggleButtonGroupUiState,
) : ToggleButtonGroupWithTitleUiState(
        isVisible = isVisible,
        title = title,
        toggleButtonGroup = toggleButtonGroup,
    )

data class CaffeineOptionToggleButtonGroupUiState(
    override val isVisible: Boolean,
    override val title: String = "디카페인",
//    override val title: String = Resources.getSystem().getString(R.string.decaffeine),
    override val toggleButtonGroup: ToggleButtonGroupUiState,
) : ToggleButtonGroupWithTitleUiState(
        isVisible = isVisible,
        title = title,
        toggleButtonGroup = toggleButtonGroup,
    )

data class IceOptionToggleButtonGroupUiState(
    override val isVisible: Boolean,
    override val title: String = "얼음",
//    override val title: String = Resources.getSystem().getString(R.string.ice),
    override val toggleButtonGroup: ToggleButtonGroupUiState,
) : ToggleButtonGroupWithTitleUiState(
        isVisible = isVisible,
        title = title,
        toggleButtonGroup = toggleButtonGroup,
    )
