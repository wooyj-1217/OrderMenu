package com.wooyj.ordermenu.ui.screen.option

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.data.menuTypeFromId
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                val menuType = savedStateHandle.get<Int>("menuId")!!.menuTypeFromId()

                _uiState.value =
                    UiState.Success(data = MenuOptionUiState(menuType = menuType))
            }
        }

        fun clickTempOption(tempOption: OptionButtonUiState) {
            if (uiState.value is UiState.Success) {
                val currentState = _uiState.value as UiState.Success
                val newTempOption = TempOption.fromString(tempOption.optionName)
                val newIceOption = IceOption.Small

                _uiState.update {
                    currentState.copy(
                        data =
                            currentState.data.copy(
                                tempOption = newTempOption,
                                iceOption = newIceOption,
                            ),
                    )
                }
            }
        }

        fun clickCaffeineOption(caffeineOption: OptionButtonUiState) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (_uiState.value as UiState.Success).copy(
                        data =
                            (_uiState.value as UiState.Success).data.copy(
                                caffeineOption = CaffeineOption.fromString(caffeineOption.optionName),
                            ),
                    )
                }
            }
        }

        fun clickIceOption(iceOption: OptionButtonUiState) {
            if (uiState.value is UiState.Success) {
                _uiState.update {
                    (_uiState.value as UiState.Success).copy(
                        data =
                            (_uiState.value as UiState.Success).data.copy(
                                iceOption = IceOption.fromString(iceOption.optionName),
                            ),
                    )
                }
            }
        }
    }
