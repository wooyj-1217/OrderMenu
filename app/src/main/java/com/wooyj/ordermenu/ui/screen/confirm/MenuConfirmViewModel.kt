package com.wooyj.ordermenu.ui.screen.confirm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MenuConfirmViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private var _uiState = MutableStateFlow<UiState<MenuConfirmUiState>>(UiState.Loading)
        val uiState: StateFlow<UiState<MenuConfirmUiState>> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val orderOption = Json.decodeFromString<OrderOption>(savedStateHandle.get<String>("option")!!)
                Log.d("OrderOption", "$orderOption")
                _uiState.value = UiState.Success(data = MenuConfirmUiState(orderOption = orderOption))
            }
        }
    }