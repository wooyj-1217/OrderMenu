package com.wooyj.ordermenu.ui.screen.list.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wooyj.ordermenu.data.MenuType

@Composable
fun MenuListUI(
    menuList: List<MenuType>,
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val groupedMap = menuList.groupBy { it.javaClass.simpleName }
    LazyColumn(modifier.fillMaxSize()) {
        groupedMap.forEach { (header, list) ->
            item {
                MenuItem(header = header, list = list, onMenuClick = onMenuClick)
                Divider(
                    modifier =
                        Modifier
                            .background(Color.LightGray)
                            .height(8.dp),
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
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        MenuItemHeader(header = header)
        list.forEachIndexed { index, menuType ->
            MenuItemDetail(menuType, onMenuClick)
            if (index != list.size - 1) {
                Divider(
                    modifier =
                        Modifier
                            .background(Color.LightGray)
                            .height(1.dp),
                )
            }
        }
    }
}

@Composable
fun MenuItemHeader(
    header: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = header, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MenuItemDetail(
    item: MenuType,
    onMenuClick: (MenuType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable { onMenuClick(item) }
                .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(item.menuName)
        Text("${item.price.addCommasToNumber()}원")
    }
}
