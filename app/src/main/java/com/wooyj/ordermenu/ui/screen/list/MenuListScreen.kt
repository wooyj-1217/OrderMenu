package com.wooyj.ordermenu.ui.screen.list

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
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.menuList
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBar
import com.wooyj.ordermenu.ui.screen.common.appbar.AppNavBarUiState
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.list.state.MenuListUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuListScreen(
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuListViewModel = hiltViewModel(),
    backIconClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppNavBar(uiState = AppNavBarUiState.List, navAction = backIconClick)
        },
        content = {
            when (uiState) {
                is UiState.Success -> {
                    MenuListUI(
                        modifier = Modifier.padding(it),
                        onMenuClick = onMenuClick,
                        menuList = (uiState as UiState.Success).data.menuList,
                    )
                }
                else -> Unit
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuListScreen() {
    OrderMenuTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content = {
                MenuListUI(
                    modifier = Modifier.padding(it),
                    onMenuClick = { _ -> },
                    menuList = menuList,
                )
            },
        )
    }
}
