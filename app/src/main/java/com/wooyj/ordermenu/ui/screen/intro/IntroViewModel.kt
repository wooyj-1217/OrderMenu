package com.wooyj.ordermenu.ui.screen.intro

import android.app.Application
import androidx.lifecycle.ViewModel
import com.wooyj.ordermenu.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class IntroViewModel
    @Inject
    constructor(
        application: Application,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<IntroUiState> = MutableStateFlow(IntroUiState.None)
        val uiState = _uiState.asStateFlow()

        init {
            _uiState.value = IntroUiState.Success(text = application.getString(R.string.intro_message))
        }
    }
