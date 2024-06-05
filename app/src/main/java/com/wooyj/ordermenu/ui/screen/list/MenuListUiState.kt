package com.wooyj.ordermenu.ui.screen.list

import com.wooyj.ordermenu.ui.screen.list.model.MenuTypeUI

sealed class MenuListUiState {
    data object None : MenuListUiState()

    data object Loading : MenuListUiState()

    data class Success(val menuList: List<Menu>) : MenuListUiState()

    data class Error(val exception: Throwable) : MenuListUiState()
}

data class Menu(
    val header: Int,
    val menu: List<MenuTypeUI>,
)
