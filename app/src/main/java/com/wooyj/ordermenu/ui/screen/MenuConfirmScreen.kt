package com.wooyj.ordermenu.ui.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.state.UiState
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuConfirmScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    OrderMenuTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("menu") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content = {
                MenuConfirmUI(
                    modifier = Modifier.padding(it),
                    viewModel = hiltViewModel(),
                    onCloseButtonClicked = {
                        navController?.navigate("menu") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                )
            },
        )
    }
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
                    onCloseButtonClicked = {},
                )
            },
        )
    }
}

@Composable
fun MenuConfirmUI(
    modifier: Modifier = Modifier,
    viewModel: MenuConfirmViewModel = viewModel(),
    onCloseButtonClicked: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Success -> {
            val data = (uiState as UiState.Success<OrderOption>).data
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
                        Text(data.menuType.menuName)
                        Text(data.getOptionString())
                    }
                    Text("${data.menuType.price.addCommasToNumber()}원")
                }
                Button(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(16.dp),
                    onClick = onCloseButtonClicked,
                ) {
                    Text("닫기", fontSize = 20.sp)
                }
            }
        }
        is UiState.Error -> {}
        is UiState.Loading -> {}
    }
}
