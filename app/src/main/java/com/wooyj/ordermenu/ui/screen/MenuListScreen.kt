package com.wooyj.ordermenu.ui.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.menuTypeJson
import com.wooyj.ordermenu.ui.state.UiState
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuListScreen(navController: NavController, modifier: Modifier = Modifier) {

    OrderMenuTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content =
            {
                MenuListUI(
                    modifier = Modifier.padding(it),
                    onMenuClick = { menu ->
                        //error >> java.lang.IllegalArgumentException: Navigation destination that matches request
                        // NavDeepLinkRequest{ uri=android-app://androidx.navigation/menu/select/
                        // {"type":"com.wooyj.ordermenu.data.MenuType.Coffee","menuName":"아메리카노","price":1000} }
                        // cannot be found in the navigation graph ComposeNavGraph(0x0) startDestination={Destination(0x78da666c) route=menu}

                        // log를 보면 {"type":"com.wooyj.ordermenu.data.MenuType.Coffee","menuName":"아메리카노","price":1000} }
                        // 왜 temp list, caffeine list, ice list는 적용이 안된걸까.
                        // solved. @Transient 를 name과 price에만 주면 해결됨.
                        // 참고 : https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#designing-serializable-hierarchy

//                        Log.d(
//                            "MenuListScreen",
//                            menuTypeJson.encodeToString(MenuType.serializer(), menu)
//                        )
//                        val menuType =
//                            Uri.encode(menuTypeJson.encodeToString(MenuType.serializer(), menu))
//                        navController.navigate(route = "menu/select/${menuType}")

                        //이렇게 삽질했는데...
                        //https://medium.com/@edmiro/type-safety-in-navigation-compose-23c03e3d74a5
//                                  navController.navigate(menu)
//                        navController.navigate(
//                            OrderOption(
//                                menuType = menu,
//                                tempOption = if (menu.listTempOption.isNotEmpty()) {
//                                    menu.listTempOption[0]
//                                } else null,
//                                caffeineOption = if (menu.listCaffeineOption.isNotEmpty()) {
//                                    menu.listCaffeineOption[0]
//                                } else null,
//                                iceOption = if (menu.listIceOption.isNotEmpty()) {
//                                    menu.listIceOption[0]
//                                } else null
//                            )
//                        )


                        val serializeEncodeString = menuTypeJson.encodeToString(
                            OrderOption.serializer(),
                            OrderOption(
                                menuType = menu,
                                tempOption = if (menu.listTempOption.isNotEmpty()) {
                                    menu.listTempOption[0]
                                } else null,
                                caffeineOption = if (menu.listCaffeineOption.isNotEmpty()) {
                                    menu.listCaffeineOption[0]
                                } else null,
                                iceOption = if (menu.listIceOption.isNotEmpty()) {
                                    menu.listIceOption[0]
                                } else null
                            )
                        )
                        Log.d("screen navigation", "$serializeEncodeString")
                        val orderOption = URLEncoder.encode(
                            serializeEncodeString, StandardCharsets.UTF_8.toString()
                        )
                        Log.d("screen navigation / url encoded", "$orderOption")
                        navController.navigate(route = "menu/select/$orderOption")

                    },
                    viewModel = hiltViewModel()
                )

            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMenuListScreen() {
    OrderMenuTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            },
            content =
            {
                MenuListUI(
                    modifier = Modifier.padding(it),
                    onMenuClick = { _ -> })
            })
    }
}

@Composable
fun MenuListUI(
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuListViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            val groupedMap =
                (uiState as UiState.Success<List<MenuType>>).data.groupBy { it.javaClass.simpleName }
            LazyColumn(modifier.fillMaxSize()) {
                groupedMap.forEach { header, list ->
                    item {
                        MenuItem(header = header, list = list, onMenuClick = onMenuClick)
                        Divider(
                            modifier = Modifier
                                .background(Color.LightGray)
                                .height(8.dp)
                        )
                    }
                }
            }
        }

        is UiState.Error -> {}
    }

}


@Composable
fun MenuItem(
    header: String,
    list: List<MenuType>,
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        MenuItemHeader(header = header)
        list.forEachIndexed { index, menuType ->
            MenuItemDetail(menuType, onMenuClick)
            if (index != list.size - 1) {
                Divider(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .height(1.dp)
                )
            }
        }
    }

}


@Composable
fun MenuItemHeader(header: String, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(header, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MenuItemDetail(item: MenuType, onMenuClick: (MenuType) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onMenuClick(item) }
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(item.menuName)
        Text("${item.price.addCommasToNumber()}원")
    }
}