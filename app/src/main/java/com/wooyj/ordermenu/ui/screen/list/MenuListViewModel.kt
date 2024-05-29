package com.wooyj.ordermenu.ui.screen.list

import androidx.lifecycle.ViewModel
import com.wooyj.ordermenu.data.menuList
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.list.model.MenuTypeUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel
    @Inject
    constructor() : ViewModel() {
        private var _uiState = MutableStateFlow<UiState<MenuListUiState>>(UiState.None)
        val uiState: StateFlow<UiState<MenuListUiState>> = _uiState.asStateFlow()

        init {
            val data =
                menuList
                    .map { MenuTypeUI.fromEntity(it) }
                    .groupBy { it.menuType }
                    .map { (header, list) ->
                        Menu(header = header, menu = list)
                    }
            _uiState.value = UiState.Success(data = MenuListUiState(menuList = data))
        }
    }
