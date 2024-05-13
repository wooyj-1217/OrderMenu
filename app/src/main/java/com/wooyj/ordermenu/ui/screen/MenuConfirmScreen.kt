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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import com.wooyj.ordermenu.utils.addCommasToNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuConfirmScreen(
    navController: NavController,
    result: String?,
    menuName: String,
    price: Int,
    modifier: Modifier = Modifier
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
            content =
            {
                MenuConfirmUI(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    result = result,
                    menuName = menuName,
                    price = price
                )
            })
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
            content =
            {
                MenuConfirmUI(
                    modifier = Modifier.padding(it),
                    navController = null,
                    result = "ICE/디카페인/얼음(적게)",
                    menuName = "아메리카노",
                    price = 1000
                )
            })
    }
}


@Composable
fun MenuConfirmUI(
    navController: NavController?,
    result: String?,
    menuName: String,
    price: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(menuName)
                result?.let {
                    Text(it.removeSurrounding("[", "]").replace(",", "/"))
                }
            }
            Text("${price.addCommasToNumber()}원")
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(16.dp), onClick = {

            navController?.navigate("menu") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }) {
            Text("닫기", fontSize = 20.sp)
        }

    }
}