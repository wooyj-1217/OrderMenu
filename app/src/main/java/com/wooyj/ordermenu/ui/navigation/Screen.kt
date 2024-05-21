package com.wooyj.ordermenu.ui.navigation

import com.wooyj.ordermenu.data.OrderOption
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Intro : Screen

    @Serializable
    data object MenuList : Screen

    @Serializable
    data class SelectOption(val option: OrderOption) : Screen

    @Serializable
    data class ConfirmOrder(val option: OrderOption) : Screen
}
