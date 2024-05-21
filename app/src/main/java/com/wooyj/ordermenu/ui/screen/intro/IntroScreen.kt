package com.wooyj.ordermenu.ui.screen.intro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.intro.state.IntroUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    viewModel: IntroViewModel = hiltViewModel(),
    onNextNavigation: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        when (uiState) {
            is UiState.Success -> {
                IntroUI(
                    modifier = Modifier.padding(innerPadding),
                    onNextNavigation = onNextNavigation,
                    titleText = (uiState as UiState.Success<IntroUiState>).data.text,
                    buttonText = (uiState as UiState.Success<IntroUiState>).data.buttonText,
                )
            }

            else -> Unit
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewIntroScreen() {
    OrderMenuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            IntroUI(onNextNavigation = {}, titleText = "반가워요\n주문을 시작할게요", buttonText = "다음")
        }
    }
}