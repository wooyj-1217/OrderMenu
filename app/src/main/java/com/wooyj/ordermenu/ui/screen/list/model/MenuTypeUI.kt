package com.wooyj.ordermenu.ui.screen.list.model

import com.wooyj.ordermenu.R
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.Price
import com.wooyj.ordermenu.data.menuIdFromMenuName

data class MenuTypeUI(
    val menuName: String,
    val price: Price,
    val menuType: Int,
) {
    fun toEntity(): MenuType =
        when (menuType) {
            R.string.coffee ->
                MenuType.Coffee(
                    menuName = menuName,
                    price = price,
                    id = menuName.menuIdFromMenuName(),
                )
            R.string.beverage ->
                MenuType.Beverage(
                    menuName = menuName,
                    price = price,
                    id = menuName.menuIdFromMenuName(),
                )
            R.string.dessert ->
                MenuType.Dessert(
                    menuName = menuName,
                    price = price,
                    id = menuName.menuIdFromMenuName(),
                )
            R.string.tea ->
                MenuType.Tea(
                    menuName = menuName,
                    price = price,
                    id = menuName.menuIdFromMenuName(),
                )
            else -> throw IllegalArgumentException("Unknown menu type")
        }

    companion object {
        fun fromEntity(menuType: MenuType): MenuTypeUI =
            MenuTypeUI(
                menuName = menuType.menuName,
                price = menuType.price,
                menuType =
                    when (menuType) {
                        is MenuType.Coffee -> R.string.coffee
                        is MenuType.Beverage -> R.string.beverage
                        is MenuType.Dessert -> R.string.dessert
                        is MenuType.Tea -> R.string.tea
                    },
            )
    }
}
