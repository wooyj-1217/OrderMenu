package com.wooyj.ordermenu.ui.screen.intro

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBar
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBarUiState
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
            AppNavBar(uiState = AppNavBarUiState.Intro, navAction = {})
        },
    ) { innerPadding ->
        when (uiState) {
            is IntroUiState.Success -> {
                IntroUI(
                    modifier = modifier.padding(innerPadding),
                    onNextNavigation = onNextNavigation,
                    titleText = (uiState as IntroUiState.Success).text,
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
        IntroScreen(
            onNextNavigation = {},
        )
    }
}
