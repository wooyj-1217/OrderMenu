package com.wooyj.ordermenu.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.confirm.MenuConfirmScreen
import com.wooyj.ordermenu.ui.screen.intro.IntroScreen
import com.wooyj.ordermenu.ui.screen.list.MenuListScreen
import com.wooyj.ordermenu.ui.screen.option.MenuOptionScreen
import kotlin.reflect.typeOf

@Composable
fun OrderMenuNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Intro,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier,
    ) {
        composable<Screen.Intro> {
            IntroScreen(onNextNavigation = { navController.navigate(Screen.MenuList) })
        }
        composable<Screen.MenuList> {
            MenuListScreen(
                backIconClick = {
                    navController.popBackStack()
                },
                onMenuClick = { menu ->
                    val orderOption =
                        OrderOption(
                            menuType = menu,
                            tempOption =
                                if (menu.listTempOption.isNotEmpty()) {
                                    menu.listTempOption[0]
                                } else {
                                    null
                                },
                            caffeineOption =
                                if (menu.listCaffeineOption.isNotEmpty()) {
                                    menu.listCaffeineOption[0]
                                } else {
                                    null
                                },
                            iceOption =
                                if (menu.listIceOption.isNotEmpty()) {
                                    menu.listIceOption[0]
                                } else {
                                    null
                                },
                        )
                    navController.navigate(Screen.SelectOption(option = orderOption))
                },
            )
        }
        composable<Screen.SelectOption>(
            typeMap =
                mapOf(
                    typeOf<OrderOption>() to NavTypeOrderOption,
                ),
        ) {
            MenuOptionScreen(
                backIconClick = {
                    navController.popBackStack()
                },
                onNextClick = { option ->
                    navController.navigate(Screen.ConfirmOrder(option = option))
                },
            )
        }
        composable<Screen.ConfirmOrder>(
            typeMap =
                mapOf(
                    typeOf<OrderOption>() to NavTypeOrderOption,
                ),
        ) {
            MenuConfirmScreen(
                goIntro = {
                    navController.navigate(Screen.Intro) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
            )
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
