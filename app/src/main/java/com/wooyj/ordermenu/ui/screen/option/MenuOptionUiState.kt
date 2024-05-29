package com.wooyj.ordermenu.ui.screen.option.model

import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.Price
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.togglebutton.CaffeineOptionToggleButtonGroupUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.IceOptionToggleButtonGroupUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.TempOptionToggleButtonGroupUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonGroupUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonUiState

data class MenuOptionUiState(
    val menuName: String,
    val price: Price,
    val menuType: String,
    val tempOptionList: TempOptionToggleButtonGroupUiState,
    val caffeineOptionList: CaffeineOptionToggleButtonGroupUiState,
    val iceOptionList: IceOptionToggleButtonGroupUiState,
//    val optionList : List<ToggleButtonGroupWithTitleUiState>,
) {
    fun toEntity(): OrderOption {
        val menuType =
            when (menuType) {
                MenuType.Coffee.toString() -> MenuType.Coffee(menuName, price)
                MenuType.Beverage.toString() -> MenuType.Beverage(menuName, price)
                MenuType.Tea.toString() -> MenuType.Tea(menuName, price)
                MenuType.Dessert.toString() -> MenuType.Dessert(menuName, price)
                else -> throw IllegalArgumentException("Unknown menu type")
            }
        var tempOption: TempOption? = null
        var caffeineOption: CaffeineOption? = null
        var iceOption: IceOption? = null

        tempOptionList.toggleButtonGroup.list.forEach {
            if (it.isSelected) {
                tempOption = TempOption.fromString(it.optionName)
            }
        }
        caffeineOptionList.toggleButtonGroup.list.forEach {
            if (it.isSelected) {
                caffeineOption = CaffeineOption.fromString(it.optionName)
            }
        }
        if (tempOption == TempOption.Ice) {
            iceOptionList.toggleButtonGroup.list.forEach {
                if (it.isSelected) {
                    iceOption = IceOption.fromString(it.optionName)
                }
            }
        }

        return OrderOption(
            menuType = menuType,
            tempOption = tempOption,
            caffeineOption = caffeineOption,
            iceOption = iceOption,
        )
    }

    companion object {
        fun fromEntity(menuType: MenuType): MenuOptionUiState =
            MenuOptionUiState(
                menuName = menuType.menuName,
                price = menuType.price,
                menuType =
                    when (menuType) {
                        is MenuType.Coffee -> MenuType.Coffee.toString()
                        is MenuType.Beverage -> MenuType.Beverage.toString()
                        is MenuType.Dessert -> MenuType.Dessert.toString()
                        is MenuType.Tea -> MenuType.Tea.toString()
                    },
                tempOptionList =
                    if (menuType.listTempOption.isNotEmpty()) {
                        TempOptionToggleButtonGroupUiState(
                            isVisible = true,
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list =
                                        menuType.listTempOption.mapIndexed { index, item ->
                                            ToggleButtonUiState(
                                                optionName = item.toString(),
                                                isSelected = index == 0,
                                            )
                                        },
                                ),
                        )
                    } else {
                        TempOptionToggleButtonGroupUiState(
                            isVisible = false,
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list = listOf(),
                                ),
                        )
                    },
                caffeineOptionList =
                    if (menuType.listCaffeineOption.isNotEmpty()) {
                        CaffeineOptionToggleButtonGroupUiState(
                            isVisible = true,
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list =
                                        menuType.listCaffeineOption.mapIndexed { index, item ->
                                            ToggleButtonUiState(
                                                optionName = item.toString(),
                                                isSelected = index == 0,
                                            )
                                        },
                                ),
                        )
                    } else {
                        CaffeineOptionToggleButtonGroupUiState(
                            isVisible = false,
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list = listOf(),
                                ),
                        )
                    },
                iceOptionList =
                    if (menuType.listIceOption.isNotEmpty()) {
                        IceOptionToggleButtonGroupUiState(
                            isVisible = !(menuType.listTempOption.isNotEmpty() && menuType.listTempOption[0] == TempOption.Hot),
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list =
                                        menuType.listIceOption.mapIndexed { index, item ->
                                            ToggleButtonUiState(
                                                optionName = item.toString(),
                                                isSelected = index == 0,
                                            )
                                        },
                                ),
                        )
                    } else {
                        IceOptionToggleButtonGroupUiState(
                            isVisible = false,
                            toggleButtonGroup =
                                ToggleButtonGroupUiState(
                                    list = listOf(),
                                ),
                        )
                    },
            )
    }
}
