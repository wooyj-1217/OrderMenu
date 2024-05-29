package com.wooyj.ordermenu.ui.screen.confirm.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.common.button.CloseButton

@Composable
fun MenuConfirmUI(
    goIntro: () -> Unit,
    option: OrderOption,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(option.menuType.menuName)
                Text(option.toString())
            }
            Text("${option.menuType.price}원")
        }
        CloseButton(onNextNavigation = goIntro)
    }
}
