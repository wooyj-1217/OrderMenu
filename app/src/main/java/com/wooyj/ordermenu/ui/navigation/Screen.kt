package com.wooyj.ordermenu.ui.navigation

import androidx.core.net.toUri
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(
    val route: String,
) {
    data object Intro : Screen(route = "intro")

    data object MenuList : Screen(route = "menu/list")

    data object SelectOption : Screen(route = "menu/{menu}") {
        fun setOption(menuType: MenuType): String {
            val menuJsonString = Json.encodeToString(menuType).toUri()
            return "menu/$menuJsonString"
        }
    }

    data object ConfirmOrder : Screen(route = "menu/confirm/{option}") {
        fun setOption(option: OrderOption): String {
            val optionString = Json.encodeToString(option).toUri()
            return "menu/confirm/$optionString"
        }
    }
}
