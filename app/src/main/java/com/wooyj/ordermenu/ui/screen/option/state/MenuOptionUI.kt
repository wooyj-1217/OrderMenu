package com.wooyj.ordermenu.ui.screen.option.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.ui.screen.common.button.NextButton
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonWithTitle
import com.wooyj.ordermenu.ui.screen.option.model.MenuOptionUiState

@Composable
fun MenuOptionUI(
    uiState: MenuOptionUiState,
    onNextClick: (OrderOption) -> Unit,
    clickTempOption: (ToggleButtonUiState) -> Unit,
    clickCaffeineOption: (ToggleButtonUiState) -> Unit,
    clickIceOption: (ToggleButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier =
                Modifier
                    .padding(start = 20.dp, top = 60.dp, end = 20.dp)
                    .weight(1f),
        ) {
            Text(uiState.menuName)
            Text("${uiState.price}원")
            Spacer(modifier = Modifier.height(40.dp))
            ToggleButtonWithTitle(
                uiState = uiState.tempOptionList,
                onClick = clickTempOption,
            )
            ToggleButtonWithTitle(
                uiState = uiState.caffeineOptionList,
                onClick = clickCaffeineOption,
            )
            ToggleButtonWithTitle(
                uiState = uiState.iceOptionList,
                onClick = clickIceOption,
            )
        }
        NextButton(onNextNavigation = { onNextClick(uiState.toEntity()) })
    }
}
