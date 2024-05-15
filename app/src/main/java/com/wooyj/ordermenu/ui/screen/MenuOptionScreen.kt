package com.wooyj.ordermenu.ui.screen

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
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.state.UiState
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionScreen(navController: NavController, modifier: Modifier = Modifier) {
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
                    viewModel = hiltViewModel()
                )
            })
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
                    modifier = Modifier.padding(it)
                )
            })
    }
}


@Composable
fun MenuOptionUI(
    modifier: Modifier = Modifier,
    viewModel: MenuOptionViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Success -> {
            Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 60.dp, end = 20.dp)
                        .weight(1f)
                ) {
                    val order = (uiState as UiState.Success<OrderOption>).data
                    val menu = order.menuType
                    Text(menu.menuName)
                    Text("${menu.price.addCommasToNumber()}원")
                    Spacer(modifier = Modifier.height(40.dp))

                    if (menu.listTempOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listTempOption,
                            title = "기본",
                            option = order.tempOption!!,
                            onChanged = { temp -> viewModel.clickTempOption(temp) })
                    }
                    if (menu.listCaffeineOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listCaffeineOption,
                            title = "기본",
                            option = order.caffeineOption!!,
                            onChanged = { caffeine -> viewModel.clickCaffeineOption(caffeine) })
                    }
                    if (menu.listIceOption.isNotEmpty()) {
                        MenuOption(
                            options = menu.listIceOption,
                            title = "",
                            option = order.iceOption!!,
                            onChanged = { ice -> viewModel.clickIceOption(ice) }
                        )
                    }
                }

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(16.dp), onClick = {
//                    val options = mutableListOf<String>()
//            if (optionTemp != "") {
//                options.add(optionTemp)
//            }
//            if (optionCaffein != "") {
//                options.add(optionCaffein)
//            }
//            if (optionIce != "") {
//                options.add("얼음($optionIce)")
//            }
//            options.joinToString(",")
//                    navController?.navigate(
//                        "menuConfirm/result=${options}&menuName=${menu.menuName}&price=${menu.price}"
//                    )
                }
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
    modifier: Modifier = Modifier
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        optionList.forEach {
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
                    .padding(4.dp),
                onClick = {
                    onChanged(it)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (it == option) Color.DarkGray else Color.LightGray
                )
            ) {
                Text(it.toString())
            }
        }
    }
}