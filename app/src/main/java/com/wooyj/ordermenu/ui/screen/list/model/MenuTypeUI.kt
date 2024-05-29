package com.wooyj.ordermenu.ui.screen.list.model

import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.Price

data class MenuTypeUI(
    val menuName: String,
    val price: Price,
    val menuType: String,
) {
    fun toEntity(): MenuType =
        when (menuType) {
            "커피" -> MenuType.Coffee(menuName, price)
            "음료" -> MenuType.Beverage(menuName, price)
            "디저트" -> MenuType.Dessert(menuName, price)
            "차" -> MenuType.Tea(menuName, price)
            else -> throw IllegalArgumentException("Unknown menu type")
        }

    companion object {
        fun fromEntity(menuType: MenuType): MenuTypeUI =
            MenuTypeUI(
                menuName = menuType.menuName,
                price = menuType.price,
                menuType =
                    when (menuType) {
                        is MenuType.Coffee -> "커피"
                        is MenuType.Beverage -> "음료"
                        is MenuType.Dessert -> "디저트"
                        is MenuType.Tea -> "차"
                    },
            )
    }
}
