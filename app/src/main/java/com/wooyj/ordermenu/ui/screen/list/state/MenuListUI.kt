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
import com.wooyj.ordermenu.ui.screen.list.model.MenuTypeUi

@Composable
fun MenuListUI(
    menuList: Map<String, List<MenuTypeUi>>,
    onMenuClick: (MenuTypeUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.fillMaxSize()) {
        menuList.forEach { (header, list) ->
            item {
                MenuHeader(header)
                list.forEach { menuType ->
                    MenuItem(menuType, onMenuClick)
                }
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
fun MenuHeader(
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
fun MenuItem(
    item: MenuTypeUi,
    onMenuClick: (MenuTypeUi) -> Unit,
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
