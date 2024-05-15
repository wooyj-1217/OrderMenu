package com.wooyj.ordermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wooyj.ordermenu.ui.navigation2.AppNavGraph
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderMenuTheme {
                // XML -> Activity -> Fragment -> HostFragment
                // Compose -> Activity -> NavHost

                /*
                // A surface container using the 'background' color from the theme
                // Layout????
                // Box(Surface), Column, Row -> Layout

                // Surface???
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    OrderMenuNavHost(
                        navController = navController,
                        startDestination = "menu",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                 */
                AppNavGraph()
            }
        }
    }
}
