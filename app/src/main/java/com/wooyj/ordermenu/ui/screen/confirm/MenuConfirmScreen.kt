package com.wooyj.ordermenu.ui.screen.confirm

import androidx.compose.foundation.layout.fillMaxSize
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
import com.wooyj.ordermenu.ui.screen.confirm.state.MenuConfirmUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun MenuConfirmScreen(
    appBarAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuConfirmViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppNavBar(uiState = AppNavBarUiState.Confirm, navAction = appBarAction)
        },
        content = {
            when (uiState) {
                is MenuConfirmUiState.Success -> {
                    MenuConfirmUI(
                        modifier = Modifier.padding(it),
                        goIntro = appBarAction,
                        option = (uiState as MenuConfirmUiState.Success).orderOption,
                    )
                }

                else -> Unit
            }
        },
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuConfirmScreen() {
    OrderMenuTheme {
        MenuConfirmScreen(appBarAction = {})
    }
}
