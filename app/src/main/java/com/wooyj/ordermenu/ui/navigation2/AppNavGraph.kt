package com.wooyj.ordermenu.ui.navigation2

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wooyj.ordermenu.ui.intro.IntroScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation.INTRO,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = AppNavigation.INTRO) {
            IntroScreen(onNextNavigation = { navController.navigate(AppNavigation.MENU_LIST) })
        }

        composable(route = AppNavigation.MENU_LIST) {
            // MenuListScreen(navController = navController)
        }

        composable(route = AppNavigation.MENU_OPTION) {
            // MenuOptionScreen(navController = navController)
        }

        composable(route = AppNavigation.MENU_CONFIRM) {
            // MenuConfirmScreen(navController = navController)
        }
    }
}
