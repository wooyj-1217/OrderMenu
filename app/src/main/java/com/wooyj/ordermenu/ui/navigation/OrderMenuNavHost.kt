package com.wooyj.ordermenu.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wooyj.ordermenu.ui.screen.confirm.MenuConfirmScreen
import com.wooyj.ordermenu.ui.screen.intro.IntroScreen
import com.wooyj.ordermenu.ui.screen.list.MenuListScreen
import com.wooyj.ordermenu.ui.screen.option.MenuOptionScreen

@Composable
fun OrderMenuNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier,
    ) {
        composable(route = Screen.Intro.route) {
            IntroScreen(onNextNavigation = { navController.navigate(Screen.MenuList.route) })
        }
        composable(route = Screen.MenuList.route) {
            MenuListScreen(
                appBarAction = {
                    navController.popBackStack()
                },
                onMenuClick = { menu ->
                    navController.navigate(route = Screen.SelectOption.setMenuId(menu.id))
                },
            )
        }
        composable(
            route = Screen.SelectOption.route,
            arguments =
                listOf(
                    navArgument("id") { type = NavType.IntType },
                ),
        ) {
            MenuOptionScreen(
                appBarAction = {
                    navController.popBackStack()
                },
                onNextClick = { option ->
                    navController.navigate(route = Screen.ConfirmOrder.setOption(option))
                },
            )
        }
        composable(
            route = Screen.ConfirmOrder.route,
            arguments =
                listOf(
                    navArgument("option") { type = NavType.StringType },
                ),
        ) {
            MenuConfirmScreen(
                appBarAction = {
                    navController.navigate(Screen.Intro.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
