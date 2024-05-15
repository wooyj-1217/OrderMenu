package com.wooyj.ordermenu.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption
import com.wooyj.ordermenu.ui.screen.IntroScreen
import com.wooyj.ordermenu.ui.screen.MenuConfirmScreen
import com.wooyj.ordermenu.ui.screen.MenuListScreen
import com.wooyj.ordermenu.ui.screen.MenuOptionScreen
import kotlin.reflect.typeOf

@Composable
fun OrderMenuNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
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
    composable(route = "menu") {
        IntroScreen(navController = navController)
    }
    composable(route = "menu/list") {
        MenuListScreen(navController = navController)
    }
    //TODO("post 방식으로 어떻게 넘기나요?")
//    composable<MenuType>(
//        typeMap = mapOf(typeOf<MenuType>() to NavTypeMenu)
//    ){ backStackEntry ->
//        val menuType : MenuType = backStackEntry.toRoute<MenuType>()
//        MenuOptionScreen(navController = navController, menu = menuType)
//    }

    //VO를 계속 넘기려는 시도 말고 애초에 DTO를 넘겨야했는데.... 대체 무슨 삽질을 한건지.
    //Navigation 2.8.0-alpha08 부터 가능한 코드
    //https://developer.android.com/jetpack/androidx/releases/navigation#2.8.0-alpha08
    // error >> java.lang.IllegalArgumentException: Cannot cast menuType of type com.wooyj.ordermenu.data.MenuType to a NavType.
    //          Make sure to provide custom NavType for this argument.
//    composable<OrderOption>(
//        typeMap = mapOf(
//            typeOf<OrderOption>() to NavTypeOrderOption,
//            typeOf<MenuType>() to NavTypeMenu,
//            typeOf<TempOption?>() to NavTypeEnum<TempOption>(),
//            typeOf<CaffeineOption?>() to NavTypeEnum<CaffeineOption>(),
//            typeOf<IceOption?>() to NavTypeEnum<IceOption>()
//            //error >>
//        // java.lang.IllegalArgumentException: Navigation destination that matches request
//        // NavDeepLinkRequest{ uri=android-app://androidx.navigation/com.wooyj.ordermenu.data.OrderOption/
//        // {"type":"com.wooyj.ordermenu.data.MenuType.Coffee","listTempOption":["Hot","Ice"],"listCaffeineOption":["Caffeine","DeCaffeine"],
//        // "listIceOption":["Small","Medium","Large"],"menuName":"아메리카노","price":1000}/HOT/카페인/적게 }
//        //  cannot be found in the navigation graph ComposeNavGraph(0x0) startDestination={Destination(0x78da666c) route=menu}
//        // 대체 뭔데..?
//        )
//    ) { _ ->
//        //viewModel에서 처리 시 parameter 넘길 필요 없음.
////        val orderOption :OrderOption = backStackEntry.toRoute()
//        MenuOptionScreen(navController = navController)
//    }
    composable(
        route = "menu/select/{orderOption}",
//        route = "menu/select",
        arguments = listOf(navArgument("orderOption") {
            type = NavTypeOrderOption
        })
    ) { backStackEntry ->
        val data = backStackEntry.arguments?.getParcelable<OrderOption>("orderOption")
        Log.d("OrderMenuScreen","$data")
        MenuOptionScreen(navController = navController)
    }
    composable(
        route = "menuConfirm/{orderOption}",
        arguments = listOf(navArgument("orderOption") {
            type = NavTypeOrderOption
        })
        ) { _ ->
            MenuConfirmScreen(
                navController = navController,
            )
        }

}