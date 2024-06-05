package com.wooyj.ordermenu.ui.screen.option

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.data.menuTypeFromId
import com.wooyj.ordermenu.data.option.CaffeineOption
import com.wooyj.ordermenu.data.option.IceOption
import com.wooyj.ordermenu.data.option.TempOption
import com.wooyj.ordermenu.ui.screen.common.button.option.OptionButtonUiState
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
        private var _uiState = MutableStateFlow<MenuOptionUiState>(MenuOptionUiState.None)
        val uiState: StateFlow<MenuOptionUiState> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val menuType = savedStateHandle.get<Int>("menuId")!!.menuTypeFromId()

                _uiState.value =
                    MenuOptionUiState.Success(menuType = menuType)
            }
        }

        fun sendEvents(event: MenuOptionEvent) {
            when (event) {
                is MenuOptionEvent.ClickTempOption -> clickTempOption(event.tempOption)
                is MenuOptionEvent.ClickCaffeineOption -> clickCaffeineOption(event.caffeineOption)
                is MenuOptionEvent.ClickIceOption -> clickIceOption(event.iceOption)
            }
        }

        private fun clickTempOption(tempOption: OptionButtonUiState) {
            if (uiState.value is MenuOptionUiState.Success) {
                val currentState = _uiState.value as MenuOptionUiState.Success
                val newTempOption = TempOption.fromString(tempOption.optionName)
                val newIceOption = IceOption.Small

                _uiState.update {
                    currentState.copy(
                        tempOption = newTempOption,
                        iceOption = newIceOption,
                    )
                }
            }
        }

        private fun clickCaffeineOption(caffeineOption: OptionButtonUiState) {
            if (uiState.value is MenuOptionUiState.Success) {
                _uiState.update {
                    (_uiState.value as MenuOptionUiState.Success).copy(
                        caffeineOption = CaffeineOption.fromString(caffeineOption.optionName),
                    )
                }
            }
        }

        private fun clickIceOption(iceOption: OptionButtonUiState) {
            if (uiState.value is MenuOptionUiState.Success) {
                _uiState.update {
                    (_uiState.value as MenuOptionUiState.Success).copy(
                        iceOption = IceOption.fromString(iceOption.optionName),
                    )
                }
            }
        }
    }
