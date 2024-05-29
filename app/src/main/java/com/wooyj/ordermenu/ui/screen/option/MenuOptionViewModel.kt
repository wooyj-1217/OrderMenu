package com.wooyj.ordermenu.ui.screen.option

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonUiState
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.option.model.MenuOptionUiState
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
                _uiState.value =
                    UiState.Success(data = MenuOptionUiState.fromEntity(orderOption.menuType))
            }
        }

        fun clickTempOption(tempOption: ToggleButtonUiState) {
            if (uiState.value is UiState.Success) {
                val successData = (_uiState.value as UiState.Success)
                val data = successData.data

                val updatedTempOption =
                    data.tempOptionList.copy(
                        toggleButtonGroup = data.tempOptionList.toggleButtonGroup.selectOption(tempOption.optionName),
                    )
                val updatedIceOption =
                    if (tempOption.optionName == TempOption.Hot.toString()) {
                        data.iceOptionList.copy(
                            isVisible = false,
                            toggleButtonGroup = data.iceOptionList.toggleButtonGroup.selectOption(IceOption.Small.toString()),
                        )
                    } else {
                        data.iceOptionList.copy(isVisible = true)
                    }

                _uiState.update {
                    successData.copy(
                        data =
                            data.copy(
                                tempOptionList = updatedTempOption,
                                iceOptionList = updatedIceOption,
                            ),
                    )
                }
            }
        }

        fun clickCaffeineOption(caffeineOption: ToggleButtonUiState) {
            if (uiState.value is UiState.Success) {
                val successData = (_uiState.value as UiState.Success)
                val data = successData.data

                val updatedCaffeineOption =
                    data.caffeineOptionList.copy(
                        toggleButtonGroup = data.caffeineOptionList.toggleButtonGroup.selectOption(caffeineOption.optionName),
                    )

                _uiState.update {
                    successData.copy(
                        data =
                            data.copy(
                                caffeineOptionList = updatedCaffeineOption,
                            ),
                    )
                }
            }
        }

        fun clickIceOption(iceOption: ToggleButtonUiState) {
            if (uiState.value is UiState.Success) {
                val successData = (_uiState.value as UiState.Success)
                val data = successData.data

                val updatedIceOption =
                    data.iceOptionList.copy(
                        toggleButtonGroup = data.iceOptionList.toggleButtonGroup.selectOption(iceOption.optionName),
                    )

                _uiState.update {
                    successData.copy(
                        data =
                            data.copy(
                                iceOptionList = updatedIceOption,
                            ),
                    )
                }
            }
        }
    }
