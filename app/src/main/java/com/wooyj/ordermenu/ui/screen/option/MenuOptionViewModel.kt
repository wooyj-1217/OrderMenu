package com.wooyj.ordermenu.ui.screen.option

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
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
        private var _uiState = MutableStateFlow<UiState<MenuOptionUiState>>(UiState.None)
        val uiState: StateFlow<UiState<MenuOptionUiState>> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val orderOption =
                    Json.decodeFromString<OrderOption>(savedStateHandle.get<String>("option")!!)
                _uiState.value = UiState.Success(data = MenuOptionUiState(orderOption = orderOption))
            }
        }

        fun clickTempOption(tempOption: TempOption) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (it as UiState.Success<MenuOptionUiState>).copy(
                        data =
                            (uiState.value as UiState.Success<MenuOptionUiState>).data.copy(
                                orderOption =
                                    (uiState.value as UiState.Success<MenuOptionUiState>).data.orderOption.copy(
                                        tempOption = tempOption,
                                        iceOption = if (tempOption == TempOption.Hot) null else IceOption.Small,
                                    ),
                            ),
                    )
                }
            }
        }

        fun clickCaffeineOption(caffeineOption: CaffeineOption) {
            _uiState.update {
                (it as UiState.Success<MenuOptionUiState>).copy(
                    data =
                        (uiState.value as UiState.Success<MenuOptionUiState>).data.copy(
                            orderOption =
                                (uiState.value as UiState.Success<MenuOptionUiState>).data.orderOption.copy(
                                    caffeineOption = caffeineOption,
                                ),
                        ),
                )
            }
        }

        fun clickIceOption(iceOption: IceOption) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (it as UiState.Success<MenuOptionUiState>).copy(
                        data =
                            (uiState.value as UiState.Success<MenuOptionUiState>).data.copy(
                                orderOption =
                                    (uiState.value as UiState.Success<MenuOptionUiState>).data.orderOption.copy(
                                        iceOption = iceOption,
                                    ),
                            ),
                    )
                }
            }
        }
    }
