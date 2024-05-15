package com.wooyj.ordermenu.ui.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.navigation.NavTypeOrderOption
import com.wooyj.ordermenu.ui.state.UiState
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // 잘 받아오는데 뷰모델 savedStateHandle은 왜 안받아오냐...? 무슨일이야..?
//    Log.d(
//        "MenuOptionScreen",
//        "${navController.previousBackStackEntry?.savedStateHandle?.get<OrderOption>("orderOption")}"
//    )
//    Log.d(
//        "MenuOptionScreen",
//        "${navController.previousBackStackEntry?.savedStateHandle?.get<OrderOption>("orderOption")?.menuType?.menuName}"
//    )
//    Log.d("MenuOptionScreen","${navController.previousBackStackEntry?.savedStateHandle}")
    OrderMenuTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content =
                {
                    MenuOptionUI(
                        modifier = Modifier.padding(it),
                        viewModel = hiltViewModel<MenuOptionViewModel>(),
                        onNextClick = { option ->
                            val encoded =
                                Uri.encode(
                                    NavTypeOrderOption.serializeAsValue(option),
                                )
                            navController.navigate("menuConfirm/$encoded")
                        },
                    )
                },
        )
    }
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
            content =
                {
                    MenuOptionUI(
                        modifier = Modifier.padding(it),
                        onNextClick = { _ -> },
                    )
                },
        )
    }
}

@Composable
fun MenuOptionUI(
    modifier: Modifier = Modifier,
    viewModel: MenuOptionViewModel = viewModel(),
    onNextClick: (OrderOption) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Success -> {
            val order = (uiState as UiState.Success<OrderOption>).data
            val menu = order.menuType

            Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
                Column(
                    modifier =
                        Modifier
                            .padding(start = 20.dp, top = 60.dp, end = 20.dp)
                            .weight(1f),
                ) {
                    Text(menu.menuName)
                    Text("${menu.price.addCommasToNumber()}원")
                    Spacer(modifier = Modifier.height(40.dp))

                    if (menu.listTempOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listTempOption,
                            title = "기본",
                            option = order.tempOption!!,
                            onChanged = { temp -> viewModel.clickTempOption(temp) },
                        )
                    }
                    if (menu.listCaffeineOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listCaffeineOption,
                            title = "디카페인",
                            option = order.caffeineOption!!,
                            onChanged = { caffeine -> viewModel.clickCaffeineOption(caffeine) },
                        )
                    }
                    if (order.tempOption != TempOption.Hot && menu.listIceOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listIceOption,
                            title = "얼음",
                            option = order.iceOption!!,
                            onChanged = { ice -> viewModel.clickIceOption(ice) },
                        )
                    }
                }

                Button(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(16.dp),
                    onClick = { onNextClick(order) },
                ) {
                    Text("다음", fontSize = 20.sp)
                }
            }
        }

        is UiState.Error -> {}
        is UiState.Loading -> {}
    }
}

@Composable
fun <T : Enum<T>> MenuOption(
    options: List<T>,
    title: String,
    option: T,
    onChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(title)
        ToggleButtonGroup(optionList = options, option = option, onChanged = onChanged)
    }
}

@Composable
fun <T : Enum<T>> ToggleButtonGroup(
    optionList: List<T>,
    option: T,
    onChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        optionList.forEach {
            Button(
                modifier =
                    Modifier
                        .height(60.dp)
                        .weight(1f)
                        .padding(4.dp),
                onClick = {
                    onChanged(it)
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (it == option) Color.DarkGray else Color.LightGray,
                    ),
            ) {
                Text(it.toString())
            }
        }
    }
}
