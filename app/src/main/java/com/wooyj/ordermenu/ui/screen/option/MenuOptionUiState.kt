package com.wooyj.ordermenu.ui.screen.option.model

import androidx.compose.runtime.Composable
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionGroupUiState

data class MenuOptionUiState(
    val menuType: MenuType, // VO
    val tempOption: TempOption? = if (menuType.listTempOption.size == 1) menuType.listTempOption[0] else null, // DTO
    val caffeineOption: CaffeineOption? = if (menuType.listCaffeineOption.size == 1) menuType.listCaffeineOption[0] else null, // DTO
    val iceOption: IceOption? = null, // DTO
) {
    fun toEntity(): OrderOption {
        return OrderOption(
            menuType = menuType,
            tempOption = tempOption,
            caffeineOption = caffeineOption,
            iceOption = iceOption,
        )
    }
}

@Composable
fun MenuOptionUiState.isVisibleTempOption(): Boolean {
    return menuType.listTempOption.size > 1
}

@Composable
fun MenuOptionUiState.getTempOptionList(): List<OptionButtonUiState> {
    return menuType.listTempOption.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = tempOption == it,
        )
    }
}

@Composable
fun MenuOptionUiState.tempOptionToggleState(): OptionGroupUiState {
    return OptionGroupUiState(
        isVisible = isVisibleTempOption(),
        title = "기본",
        optionButtonGroupUiState = ToggleButtonGroupUiState(getTempOptionList()),
    )
}

@Composable
fun MenuOptionUiState.isVisibleCaffeineOption(): Boolean {
    return menuType.listCaffeineOption.size > 1
}

@Composable
fun MenuOptionUiState.getCaffeineOptionList(): List<OptionButtonUiState> {
    return menuType.listCaffeineOption.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = caffeineOption == it,
        )
    }
}

@Composable
fun MenuOptionUiState.isVisibleIceOption(): Boolean {
    return menuType.listTempOption.contains(TempOption.Ice) &&
        tempOption == TempOption.Ice
}

@Composable
fun MenuOptionUiState.getIceOptionList(): List<OptionButtonUiState> {
    return IceOption.entries.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = iceOption == null,
        )
    }
}
