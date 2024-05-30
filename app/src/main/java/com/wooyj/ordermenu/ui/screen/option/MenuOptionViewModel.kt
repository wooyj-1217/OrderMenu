package com.wooyj.ordermenu.ui.screen.option

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionButtonUiState
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
                val menuJsonString = savedStateHandle.get<String>("menu")
                val menuType: MenuType =
                    Json.decodeFromString<MenuType>(menuJsonString ?: throw IllegalArgumentException("Menu type is null"))

                _uiState.value = UiState.Success(data = MenuOptionUiState(menuType))
            }
        }

        fun clickTempOption(tempOption: OptionButtonUiState) {
            if (uiState.value is UiState.Success) {
                _uiState.value =
                    (_uiState.value as UiState.Success).copy(
                        data =
                            (_uiState.value as UiState.Success).data.copy(
                                tempOption = TempOption.fromString(tempOption.optionName),
                            ),
                    )
            }
        }

        fun clickCaffeineOption(caffeineOption: OptionButtonUiState) {
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

        fun clickIceOption(iceOption: OptionButtonUiState) {
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
