package com.wooyj.ordermenu.ui.screen.common.togglebutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButtonGroup(
    uiState: ToggleButtonGroupUiState,
    onClick: (ToggleButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        uiState.list.forEach {
            ToggleButton(
                toggle = it,
                onClick = onClick,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(4.dp),
            )
        }
    }
}

@Composable
fun ToggleButtonWithTitle(
    uiState: ToggleButtonGroupWithTitleUiState,
    onClick: (ToggleButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isVisible) {
        Column(modifier = modifier) {
            Text(text = uiState.title)
            ToggleButtonGroup(uiState = uiState.toggleButtonGroup, onClick = onClick)
        }
    }
}
