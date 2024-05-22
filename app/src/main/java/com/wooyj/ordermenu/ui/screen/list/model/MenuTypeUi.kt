package com.wooyj.ordermenu.ui.screen.list.model

import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.Price

data class MenuTypeUi(
    val menuName: String,
    val price: Price,
    val type: String,
) {
    fun toEntity(): MenuType =
        when (type) {
            "커피" ->
                MenuType.Coffee(
                    menuName = menuName,
                    price = price,
                )
            "음료" ->
                MenuType.Beverage(
                    menuName = menuName,
                    price = price,
                )
            "디저트" ->
                MenuType.Dessert(
                    menuName = menuName,
                    price = price,
                )
            "차" ->
                MenuType.Tea(
                    menuName = menuName,
                    price = price,
                )
            else -> throw IllegalArgumentException("Invalid type: $type")
        }

    companion object {
        fun fromEntity(menuType: MenuType): MenuTypeUi =
            MenuTypeUi(
                menuName = menuType.menuName,
                price = menuType.price,
                type =
                    when (menuType) {
                        is MenuType.Coffee -> "커피"
                        is MenuType.Beverage -> "음료"
                        is MenuType.Dessert -> "디저트"
                        is MenuType.Tea -> "차"
                    },
            )
    }
}
