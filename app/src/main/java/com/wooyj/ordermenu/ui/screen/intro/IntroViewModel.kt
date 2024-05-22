package com.wooyj.ordermenu.ui.screen.intro

import androidx.lifecycle.ViewModel
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class IntroViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState: MutableStateFlow<UiState<IntroUiState>> = MutableStateFlow(UiState.None)
        val uiState = _uiState.asStateFlow()

        init {
            _uiState.value = UiState.Success(data = IntroUiState("반가워요\n주문을 시작할게요"))
        }
    }
