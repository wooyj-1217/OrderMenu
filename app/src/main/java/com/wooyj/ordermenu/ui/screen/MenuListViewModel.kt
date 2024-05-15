package com.wooyj.ordermenu.ui.screen

import androidx.lifecycle.ViewModel
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.menuList
import com.wooyj.ordermenu.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MenuListViewModel @Inject constructor() : ViewModel() {


    private var _uiState = MutableStateFlow<UiState<List<MenuType>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MenuType>>> = _uiState.asStateFlow()

    init {
        _uiState.value = UiState.Success(data = menuList)
    }


}