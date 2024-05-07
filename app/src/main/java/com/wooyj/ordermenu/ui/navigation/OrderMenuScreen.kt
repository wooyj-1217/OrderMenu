package com.wooyj.ordermenu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.ui.screen.IntroScreen
import com.wooyj.ordermenu.ui.screen.MenuConfirmScreen
import com.wooyj.ordermenu.ui.screen.MenuListScreen
import com.wooyj.ordermenu.ui.screen.MenuOptionScreen

@Composable
fun OrderMenuNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        orderMenuGraph(navController)
    }
}


fun NavGraphBuilder.orderMenuGraph(navController: NavController) {
    composable("menu") {
        IntroScreen(navController = navController)
    }
    composable("menu/list") {
        MenuListScreen(navController = navController)
    }
    composable(
        "menu/select/{menuType}",
        arguments = listOf(navArgument("menuType") {
            type = NavTypeMenu()
        })
    ) { backStackEntry ->
        val menuType = backStackEntry.arguments?.getParcelable<MenuType>("menuType")
        MenuOptionScreen(navController = navController, menu = menuType!!)
    }
    composable(
        "menuConfirm/result={result}&menuName={menuName}&price={price}", arguments = listOf(
            navArgument("result") {
                type = NavType.StringType
            },
            navArgument("menuName") {
                type = NavType.StringType
            },
            navArgument("price") {
                type = NavType.IntType
            }

        )) { backStackEntry ->

        val result =
            backStackEntry.arguments?.getString("result")
        val menuName =
            backStackEntry.arguments?.getString("menuName")
        val price = backStackEntry.arguments?.getInt("price")
        MenuConfirmScreen(
            navController = navController,
            result = result,
            menuName = menuName!!,
            price = price!!
        )
    }

}