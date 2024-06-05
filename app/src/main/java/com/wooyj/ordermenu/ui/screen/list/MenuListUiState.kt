package com.wooyj.ordermenu.ui.screen.list

import com.wooyj.ordermenu.ui.screen.list.model.MenuTypeUI

data class MenuListUiState(
    val menuList: List<Menu>,
)

data class Menu(
    val header: Int,
    val menu: List<MenuTypeUI>,
)
