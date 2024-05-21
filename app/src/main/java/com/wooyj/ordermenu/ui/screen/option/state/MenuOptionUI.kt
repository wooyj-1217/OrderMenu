package com.wooyj.ordermenu.ui.screen.option.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wooyj.ordermenu.data.CaffeineOption
import com.wooyj.ordermenu.data.IceOption
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.TempOption

@Composable
fun MenuOptionUI(
    onNextClick: (OrderOption) -> Unit,
    order: OrderOption,
    menu: MenuType,
    clickTempOption: (TempOption) -> Unit,
    clickCaffeineOption: (CaffeineOption) -> Unit,
    clickIceOption: (IceOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier =
                Modifier
                    .padding(start = 20.dp, top = 60.dp, end = 20.dp)
                    .weight(1f),
        ) {
            Text(menu.menuName)
            Text("${menu.price.addCommasToNumber()}원")
            Spacer(modifier = Modifier.height(40.dp))
            if (menu.listTempOption.isNotEmpty()) {
                MenuOption(
                    options = menu.listTempOption,
                    title = "기본",
                    option = order.tempOption!!,
                    onChanged = clickTempOption,
                )
            }
            if (menu.listCaffeineOption.isNotEmpty()) {
                MenuOption(
                    options = menu.listCaffeineOption,
                    title = "디카페인",
                    option = order.caffeineOption!!,
                    onChanged = clickCaffeineOption,
                )
            }
            if (order.tempOption != TempOption.Hot && menu.listIceOption.isNotEmpty()) {
                MenuOption(
                    options = menu.listIceOption,
                    title = "얼음",
                    option = order.iceOption!!,
                    onChanged = clickIceOption,
                )
            }
        }
        Button(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(16.dp),
            onClick = { onNextClick(order) },
        ) {
            Text("다음", fontSize = 20.sp)
        }
    }
}

@Composable
fun <T : Enum<T>> MenuOption(
    options: List<T>,
    title: String,
    option: T,
    onChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(title)
        ToggleButtonGroup(optionList = options, option = option, onChanged = onChanged)
    }
}

@Composable
fun <T : Enum<T>> ToggleButtonGroup(
    optionList: List<T>,
    option: T,
    onChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        optionList.forEach {
            Button(
                modifier =
                    Modifier
                        .height(60.dp)
                        .weight(1f)
                        .padding(4.dp),
                onClick = {
                    onChanged(it)
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = if (it == option) Color.DarkGray else Color.LightGray,
                    ),
            ) {
                Text(it.toString())
            }
        }
    }
}
