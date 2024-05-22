package com.wooyj.ordermenu.ui.screen.list

import com.wooyj.ordermenu.ui.screen.list.model.MenuTypeUi

data class MenuListUiState(
    val menuList: Map<String, List<MenuTypeUi>>,
//    val menuList: List<Menu>,
)

data class Menu(
    val header: String,
    val list: List<MenuTypeUi>,
)
