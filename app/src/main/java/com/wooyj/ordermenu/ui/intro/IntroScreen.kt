package com.wooyj.ordermenu.ui.intro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.ui.intro.state.IntroUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

// Modifiers are the beating heart of Compose UI.
// They encapsulate the idea of composition over inheritance, by allowing developers to attach logic and behavior to layouts.
@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    viewModel: IntroViewModel = hiltViewModel(),
    onNextNavigation: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // A surface container using the 'background' color from the theme
    Scaffold(
        topBar = {
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        when (uiState) {
            is IntroUiState.Intro -> {
                IntroUI(onNextNavigation = onNextNavigation)
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
            IntroUI(onNextNavigation = {})
        }
    }
}
