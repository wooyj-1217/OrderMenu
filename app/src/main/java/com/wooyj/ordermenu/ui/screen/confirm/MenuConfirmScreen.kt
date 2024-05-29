package com.wooyj.ordermenu.ui.screen.confirm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.Price
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBar
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBarUiState
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.confirm.state.MenuConfirmUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun MenuConfirmScreen(
    goIntro: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuConfirmViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppNavBar(uiState = AppNavBarUiState.Confirm, navAction = goIntro)
        },
        content = {
            when (uiState) {
                is UiState.Success -> {
                    MenuConfirmUI(
                        modifier = Modifier.padding(it),
                        goIntro = goIntro,
                        option = (uiState as UiState.Success<MenuConfirmUiState>).data.orderOption,
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
        MenuConfirmScreen(goIntro = {})
    }
}
