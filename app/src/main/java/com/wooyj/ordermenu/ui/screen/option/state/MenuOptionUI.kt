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
import com.wooyj.ordermenu.data.dto.OrderOption
import com.wooyj.ordermenu.ui.screen.common.button.bottom.NextButton
import com.wooyj.ordermenu.ui.screen.common.button.option.OptionButtonUiState
import com.wooyj.ordermenu.ui.screen.common.button.option.OptionGroupTitle
import com.wooyj.ordermenu.ui.screen.option.MenuOptionUiState
import com.wooyj.ordermenu.ui.screen.option.caffeineOptionToggleState
import com.wooyj.ordermenu.ui.screen.option.iceOptionToggleState
import com.wooyj.ordermenu.ui.screen.option.tempOptionToggleState

@Composable
fun MenuOptionUI(
    uiState: MenuOptionUiState.Success,
    onNextClick: (OrderOption) -> Unit,
    clickTempOption: (OptionButtonUiState) -> Unit,
    clickCaffeineOption: (OptionButtonUiState) -> Unit,
    clickIceOption: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
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
            OptionGroupTitle(
                uiState = uiState.tempOptionToggleState(),
                onClick = clickTempOption,
            )
            // 카페인
            OptionGroupTitle(
                uiState = uiState.caffeineOptionToggleState(),
                onClick = clickCaffeineOption,
            )
            // 얼음
            OptionGroupTitle(
                uiState = uiState.iceOptionToggleState(),
                onClick = clickIceOption,
            )
        }
        NextButton(onNextNavigation = { onNextClick(uiState.toEntity()) })
    }
}
