package com.wooyj.ordermenu.ui.screen.confirm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.Price
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.common.uistate.UiState
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(title = {}, navigationIcon = {
                IconButton(
                    onClick = goIntro,
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuConfirmScreen() {
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
                MenuConfirmUI(
                    modifier = Modifier.padding(it),
                    goIntro = {},
                    option =
                    OrderOption(
                        menuType =
                        MenuType.Coffee(
                            menuName = "아메리카노",
                            price = Price(1500),
                        ),
                        tempOption = TempOption.Hot,
                        caffeineOption = CaffeineOption.DeCaffeine,
                        iceOption = IceOption.Small,
                    ),
                )
            },
        )
    }
}

@Composable
fun MenuConfirmUI(
    goIntro: () -> Unit,
    option: OrderOption,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(option.menuType.menuName)
                Text(option.toString())
            }
            Text("${option.menuType.price.addCommasToNumber()}원")
        }
        Button(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(16.dp),
            onClick = goIntro,
        ) {
            Text("닫기", fontSize = 20.sp)
        }
    }
}
