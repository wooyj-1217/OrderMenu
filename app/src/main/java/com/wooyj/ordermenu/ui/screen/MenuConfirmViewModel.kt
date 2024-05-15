package com.wooyj.ordermenu.ui.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState<OrderOption>>(UiState.Loading)
    val uiState: StateFlow<UiState<OrderOption>> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val orderOption = savedStateHandle.get<OrderOption>("orderOption")
//            val orderOption = savedStateHandle.toRoute<OrderOption>()
            Log.d("OrderOption", "$orderOption")
            if (orderOption != null) {
                _uiState.value = UiState.Success(data = orderOption)
            } else {
                _uiState.value =
                    UiState.Error(exception = Throwable("전달받은 값이 없습니다. orderOption is $orderOption"))
            }
        }
    }

}