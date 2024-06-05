package com.wooyj.ordermenu.ui.screen.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBar
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBarUiState
import com.wooyj.ordermenu.ui.screen.list.state.MenuListUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun MenuListScreen(
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuListViewModel = hiltViewModel(),
    appBarAction: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppNavBar(uiState = AppNavBarUiState.List, navAction = appBarAction)
        },
        content = {
            when (uiState) {
                is MenuListUiState.Success -> {
                    MenuListUI(
                        modifier = Modifier.padding(it),
                        onMenuClick = { menuTypeUI ->
                            onMenuClick(menuTypeUI.toEntity())
                        },
                        menuList = (uiState as MenuListUiState.Success).menuList,
                    )
                }
                else -> Unit
            }
        },
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuListScreen() {
    OrderMenuTheme {
        MenuListScreen(
            onMenuClick = {},
            appBarAction = {},
        )
    }
}
