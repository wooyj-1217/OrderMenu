package com.wooyj.ordermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wooyj.ordermenu.ui.navigation.OrderMenuNavHost
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderMenuTheme {
                OrderMenuNavHost()
            }
        }
    }
}
