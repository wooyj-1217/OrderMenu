package com.wooyj.ordermenu.ui.screen.option

import androidx.compose.runtime.Composable
import com.wooyj.ordermenu.R
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.dto.OrderOption
import com.wooyj.ordermenu.data.option.CaffeineOption
import com.wooyj.ordermenu.data.option.IceOption
import com.wooyj.ordermenu.data.option.TempOption
import com.wooyj.ordermenu.ui.screen.common.button.option.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.button.option.OptionGroupTitleUiState

sealed class MenuOptionUiState {
    data object None : MenuOptionUiState()

    data object Loading : MenuOptionUiState()

    data class Success(
        val menuType: MenuType, // VO
        val tempOption: TempOption? = if (menuType.listTempOption.isNotEmpty()) menuType.listTempOption[0] else null, // DTO
        val caffeineOption: CaffeineOption? = if (menuType.listCaffeineOption.isNotEmpty()) menuType.listCaffeineOption[0] else null, // DTO
        val iceOption: IceOption? =
            if (menuType.listTempOption.isNotEmpty() && menuType.listTempOption[0] == TempOption.Ice) {
                IceOption.Small
            } else {
                null
            }, // DTO
    ) : MenuOptionUiState() {
        fun toEntity(): OrderOption =
            OrderOption(
                menuType = menuType,
                tempOption = tempOption,
                caffeineOption = caffeineOption,
                iceOption = iceOption,
            )
    }

    data class Error(val exception: Throwable) : MenuOptionUiState()
}

// TODO("UiState를 개별화하는 과정에서 아래와 같이 수정했더니 경고 밑줄이 계속 나와서 그런데 이거 어떻게 해결하는 것이 좋을까요?")
@Composable
fun MenuOptionUiState.Success.isVisibleTempOption(): Boolean = menuType.listTempOption.size == 2

@Composable
fun MenuOptionUiState.Success.getTempOptionList(): List<OptionButtonUiState> =
    menuType.listTempOption.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = tempOption == it,
        )
    }

@Composable
fun MenuOptionUiState.Success.tempOptionToggleState(): OptionGroupTitleUiState =
    OptionGroupTitleUiState(
        isVisible = isVisibleTempOption(),
        title = R.string.basic,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getTempOptionList(),
            ),
    )

@Composable
fun MenuOptionUiState.Success.isVisibleCaffeineOption(): Boolean = menuType.listCaffeineOption.size == 2

@Composable
fun MenuOptionUiState.Success.getCaffeineOptionList(): List<OptionButtonUiState> =
    menuType.listCaffeineOption.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = caffeineOption == it,
        )
    }

@Composable
fun MenuOptionUiState.Success.caffeineOptionToggleState(): OptionGroupTitleUiState =
    OptionGroupTitleUiState(
        isVisible = isVisibleCaffeineOption(),
        title = R.string.decaffeine,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getCaffeineOptionList(),
            ),
    )

@Composable
fun MenuOptionUiState.Success.isVisibleIceOption(): Boolean =
    menuType.listTempOption.contains(TempOption.Ice) && tempOption == TempOption.Ice

@Composable
fun MenuOptionUiState.Success.getIceOptionList(): List<OptionButtonUiState> =
    IceOption.entries.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = iceOption == it,
        )
    }

@Composable
fun MenuOptionUiState.Success.iceOptionToggleState(): OptionGroupTitleUiState =
    OptionGroupTitleUiState(
        isVisible = isVisibleIceOption(),
        title = R.string.ice,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getIceOptionList(),
            ),
    )
