package com.wooyj.ordermenu.ui.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MenuOptionViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private var _uiState = MutableStateFlow<UiState<OrderOption>>(UiState.Loading)
        val uiState: StateFlow<UiState<OrderOption>> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                Log.d("MenuOptionViewModel", "$savedStateHandle")
                val orderOption = Json.decodeFromString<OrderOption>(savedStateHandle.get<String>("option")!!)

                Log.d("MenuOptionViewModel", "$orderOption")
                if (orderOption != null) {
                    _uiState.value = UiState.Success(data = orderOption)
                } else {
                    _uiState.value =
                        UiState.Error(exception = Throwable("전달받은 값이 없습니다. orderOption is $orderOption"))
                }
            }
        }

        fun clickTempOption(tempOption: TempOption) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (it as UiState.Success<OrderOption>).copy(
                        data =
                            (uiState.value as UiState.Success<OrderOption>).data.copy(
                                tempOption = tempOption,
                                iceOption = if (tempOption == TempOption.Hot) null else IceOption.Small,
                            ),
                    )
                }
            }
        }

        fun clickCaffeineOption(caffeineOption: CaffeineOption) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (it as UiState.Success<OrderOption>).copy(
                        data =
                            (uiState.value as UiState.Success<OrderOption>).data.copy(
                                caffeineOption = caffeineOption,
                            ),
                    )
                }
            }
        }

        fun clickIceOption(iceOption: IceOption) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (it as UiState.Success<OrderOption>).copy(
                        data =
                            (uiState.value as UiState.Success<OrderOption>).data.copy(
                                iceOption = iceOption,
                            ),
                    )
                }
            }
        }
    }
