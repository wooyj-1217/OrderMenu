package com.wooyj.ordermenu.ui.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.menuList
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import com.wooyj.ordermenu.utils.addCommasToNumber

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
                    navController = navController,
                    list = menuList,
                    modifier = Modifier.padding(it)
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
                MenuListUI(modifier = Modifier.padding(it), navController = null, list = menuList)
            })
    }
}

@Composable
fun MenuListUI(
    navController: NavController?,
    list: List<MenuType>,
    modifier: Modifier = Modifier
) {

    val groupedMap = list.groupBy { it.javaClass.simpleName }
    LazyColumn(modifier.fillMaxSize()) {
        groupedMap.forEach { header, list ->
            item {
                MenuItem(header = header, list = list, onMenuClick = { menu ->

//                    val menuType = Uri.encode(GsonBuilder().create().toJson(menu))
//                    navController?.navigate("menu/select/${menuType}")
                })
                Divider(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .height(8.dp)
                )
            }
        }
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
fun MenuItemDetail(item: MenuType, onMenuClick: (MenuType) -> Unit, modifier : Modifier= Modifier) {
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