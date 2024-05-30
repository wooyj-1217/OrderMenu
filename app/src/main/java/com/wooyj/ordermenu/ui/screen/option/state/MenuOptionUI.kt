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
import com.wooyj.ordermenu.ui.screen.common.togglebutton.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.togglebutton.ToggleButtonWithTitle
import com.wooyj.ordermenu.ui.screen.option.model.MenuOptionUiState
import com.wooyj.ordermenu.ui.screen.option.model.tempOptionToggleState

@Composable
fun MenuOptionUI(
    uiState: MenuOptionUiState,
    onNextClick: (OrderOption) -> Unit,
    clickTempOption: (OptionButtonUiState) -> Unit,
    clickCaffeineOption: (OptionButtonUiState) -> Unit,
    clickIceOption: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(start = 20.dp, top = 60.dp, end = 20.dp)
                    .weight(1f),
        ) {
            Text(uiState.menuType.menuName)
            Text("${uiState.menuType.price}원")
            Spacer(modifier = Modifier.height(40.dp))
            // 온도
            ToggleButtonWithTitle(
                uiState = uiState.tempOptionToggleState(),
                onClick = clickTempOption,
            )
            // 카페인
            ToggleButtonWithTitle(
                uiState = uiState.caffeineOptionList,
                onClick = clickCaffeineOption,
            )
            // 얼음
            ToggleButtonWithTitle(
                uiState = uiState.iceOptionList,
                onClick = clickIceOption,
            )
        }
        NextButton(onNextNavigation = { onNextClick(uiState.toEntity()) })
    }
}
