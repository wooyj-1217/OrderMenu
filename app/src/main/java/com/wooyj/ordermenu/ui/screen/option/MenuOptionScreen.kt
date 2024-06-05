package com.wooyj.ordermenu.ui.screen.option

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBar
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBarUiState
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.option.state.MenuOptionUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun MenuOptionScreen(
    onNextClick: (OrderOption) -> Unit,
    appBarAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuOptionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppNavBar(uiState = AppNavBarUiState.List, navAction = appBarAction)
        },
        content = {
            when (uiState) {
                is UiState.Success -> {
                    val data = (uiState as UiState.Success<MenuOptionUiState>).data
                    MenuOptionUI(
                        uiState = data,
                        modifier = Modifier.padding(it),
                        clickTempOption = viewModel::clickTempOption,
                        clickCaffeineOption = viewModel::clickCaffeineOption,
                        clickIceOption = viewModel::clickIceOption,
                        onNextClick = onNextClick,
                    )
                }

                else -> Unit
            }
        },
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuOptionScreen() {
    OrderMenuTheme {
        MenuOptionScreen(
            onNextClick = {},
            appBarAction = {},
        )
    }
}
