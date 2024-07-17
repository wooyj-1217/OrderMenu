package com.wooyj.ordermenu.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
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
        logComposable(route = Screen.Intro.route) {
            IntroScreen(onNextNavigation = { navController.navigate(Screen.MenuList.route) })
        }
        logComposable(route = Screen.MenuList.route) {
            MenuListScreen(
                onAppBarAction = {
                    navController.popBackStack()
                },
                onMenuClick = { menu ->
                    navController.navigate(route = Screen.SelectOption.setMenuId(menu.id))
                },
            )
        }
        logComposable(
            route = Screen.SelectOption.route,
            arguments =
                listOf(
                    navArgument("menuId") { type = NavType.IntType },
                ),
        ) {
            MenuOptionScreen(
                onAppBarAction = {
                    navController.popBackStack()
                },
                onNextClick = { option ->
                    navController.navigate(route = Screen.ConfirmOrder.setOption(option))
                },
            )
        }
        logComposable(
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

private fun NavGraphBuilder.logComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable () -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
    ) {
        val myTrace = Firebase.performance.newTrace(route)
        myTrace.start()
        content()
        myTrace.stop()
    }
}

fun logScreenOpen(screenName: String) {
    val ca = FirebaseCrashlytics.getInstance()
    ca.setCustomKey("screen_view", screenName)
}
