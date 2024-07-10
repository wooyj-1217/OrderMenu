package com.wooyj.ordermenu.ui.screen.intro

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooyj.ordermenu.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel
    @Inject
    constructor(
        application: Application,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<IntroUiState> = MutableStateFlow(IntroUiState.None)
        val uiState = _uiState.asStateFlow()

        private val _effect: MutableSharedFlow<IntroEffect> = MutableSharedFlow()
        val effect = _effect.asSharedFlow()

        init {
            _uiState.value = IntroUiState.Success(text = application.getString(R.string.intro_message))
        }

        fun onEvent(event: IntroEvent) {
            when (event) {
                IntroEvent.OnNextClickEvent -> {
                    viewModelScope.launch {
                        _effect.emit(IntroEffect.NavigateToMenuList)
                    }
                }
            }
        }
    }
