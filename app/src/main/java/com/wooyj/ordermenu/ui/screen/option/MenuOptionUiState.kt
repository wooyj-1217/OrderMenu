package com.wooyj.ordermenu.ui.screen.option

import androidx.compose.runtime.Composable
import com.wooyj.ordermenu.R
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionGroupTitleUiState

data class MenuOptionUiState(
    val menuType: MenuType, // VO
    val tempOption: TempOption? = if (menuType.listTempOption.isNotEmpty()) menuType.listTempOption[0] else null, // DTO
    val caffeineOption: CaffeineOption? = if (menuType.listCaffeineOption.isNotEmpty()) menuType.listCaffeineOption[0] else null, // DTO
    val iceOption: IceOption? =
        if (menuType.listTempOption.isNotEmpty() && menuType.listTempOption[0] == TempOption.Ice) {
            IceOption.Small
        } else {
            null
        }, // DTO
) {
    fun toEntity(): OrderOption =
        OrderOption(
            menuType = menuType,
            tempOption = tempOption,
            caffeineOption = caffeineOption,
            iceOption = iceOption,
        )
}

@Composable
fun MenuOptionUiState.isVisibleTempOption(): Boolean {
    return menuType.listTempOption.isNotEmpty()
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
fun MenuOptionUiState.tempOptionToggleState(): OptionGroupTitleUiState {
    return OptionGroupTitleUiState(
        isVisible = isVisibleTempOption(),
        title = R.string.basic,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getTempOptionList(),
            ),
    )
}

@Composable
fun MenuOptionUiState.isVisibleCaffeineOption(): Boolean {
    return menuType.listCaffeineOption.isNotEmpty()
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
fun MenuOptionUiState.caffeineOptionToggleState(): OptionGroupTitleUiState {
    return OptionGroupTitleUiState(
        isVisible = isVisibleCaffeineOption(),
        title = R.string.decaffeine,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getCaffeineOptionList(),
            ),
    )
}

@Composable
fun MenuOptionUiState.isVisibleIceOption(): Boolean = menuType.listTempOption.contains(TempOption.Ice) && tempOption == TempOption.Ice

@Composable
fun MenuOptionUiState.getIceOptionList(): List<OptionButtonUiState> =
    IceOption.entries.map {
        OptionButtonUiState(
            optionName = it.toString(),
            isSelected = iceOption == it,
        )
    }

@Composable
fun MenuOptionUiState.iceOptionToggleState(): OptionGroupTitleUiState =
    OptionGroupTitleUiState(
        isVisible = isVisibleIceOption(),
        title = R.string.ice,
        optionGroupUiState =
            OptionGroupTitleUiState.OptionGroupUiState(
                list = getIceOptionList(),
            ),
    )
