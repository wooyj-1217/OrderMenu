package com.wooyj.ordermenu.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme

@Composable
fun IntroScreen(navController: NavController) {
    OrderMenuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            IntroUI(navController)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewIntroScreen() {
    OrderMenuTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            IntroUI(null)
        }
    }
}

@Composable
fun IntroUI(navController: NavController?) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.padding(start = 16.dp, top = 80.dp)) {
            Text(
                text = "반가워요",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "주문을 시작할게요",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(16.dp), onClick = {
            navController?.navigate(
                "menu/list"
            )
        }) {
            Text("다음", fontSize = 20.sp)
        }
    }
}