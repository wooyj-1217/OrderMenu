package com.wooyj.ordermenu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.IntroScreen
import com.wooyj.ordermenu.ui.screen.MenuConfirmScreen
import com.wooyj.ordermenu.ui.screen.MenuListScreen
import com.wooyj.ordermenu.ui.screen.MenuOptionScreen
import kotlin.reflect.typeOf

@Composable
fun OrderMenuNavHost(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
//        orderMenuGraph(navController)
        composable<Screen.Intro> {
            IntroScreen(navController = navController)
        }
        composable<Screen.MenuList> {
            MenuListScreen(navController = navController)
        }
        composable<Screen.SelectOption>(
            typeMap =
                mapOf(
                    typeOf<OrderOption>() to NavTypeOrderOption,
                ),
        ) {
            MenuOptionScreen(navController = navController)
        }
        composable<Screen.ConfirmOrder>(
            typeMap =
                mapOf(
                    typeOf<OrderOption>() to NavTypeOrderOption,
                ),
        ) {
            MenuConfirmScreen(navController = navController)
        }
    }
}
// TODO("Nav host 안에 Nav graph는 어떤 때 쓰나요?")
// fun NavGraphBuilder.orderMenuGraph(navController: NavController) {
//    composable<Screen.Intro> {
//        IntroScreen(navController = navController)
//    }
//    composable<Screen.MenuList> {
//        MenuListScreen(navController = navController)
//    }
//    composable<Screen.SelectOption> {
//        MenuOptionScreen(navController = navController)
//    }
//    composable<Screen.ConfirmOrder> {
//        MenuConfirmScreen(navController = navController)
//    }
// }
