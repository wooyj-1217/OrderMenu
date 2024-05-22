package com.wooyj.ordermenu.ui.screen.option

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
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.screen.option.state.MenuOptionUI
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionScreen(
    onNextClick: (OrderOption) -> Unit,
    backIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuOptionViewModel = hiltViewModel(),
) {
    // TODO("compose의 lifecycle과 기존 activity나 fragment의 lifecycle은 다른가요?")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = backIconClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        },
        content = {
            when (uiState) {
                is UiState.Success -> {
                    val order = (uiState as UiState.Success<MenuOptionUiState>).data.orderOption
                    val menu = order.menuType
                    MenuOptionUI(
                        modifier = Modifier.padding(it),
                        onNextClick = onNextClick,
                        order = order,
                        menu = menu,
                        clickTempOption = viewModel::clickTempOption,
                        clickCaffeineOption = viewModel::clickCaffeineOption,
                        clickIceOption = viewModel::clickIceOption,
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
private fun PreviewMenuOptionScreen() {
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
                MenuOptionUI(
                    onNextClick = { _ -> },
                    order =
                    OrderOption(
                        menuType =
                        MenuType.Coffee(
                            menuName = "아메리카노",
                            price = Price(1500),
                        ),
                        tempOption = TempOption.Hot,
                        caffeineOption = CaffeineOption.Caffeine,
                        iceOption = IceOption.Small,
                    ),
                    menu =
                    MenuType.Coffee(
                        menuName = "아메리카노",
                        price = Price(1500),
                    ),
                    modifier = Modifier.padding(it),
                    clickTempOption = {},
                    clickCaffeineOption = {},
                    clickIceOption = {},
                )
            },
        )
    }
}
