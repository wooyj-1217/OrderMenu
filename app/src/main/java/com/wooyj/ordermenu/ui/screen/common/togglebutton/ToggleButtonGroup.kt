package com.wooyj.ordermenu.ui.screen.common.togglebutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButtonWithTitle(
    uiState: OptionGroupUiState,
    onClick: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isVisible) {
        Column(modifier = modifier) {
            Text(text = uiState.title)
            ToggleButtonGroup(uiState = uiState.buttonList, onClick = onClick)
        }
    }
}

@Composable
fun ToggleButtonGroup(
    uiState: List<OptionButtonUiState>,
    onClick: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        uiState.forEach {
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
fun ToggleButton(
    toggle: OptionButtonUiState,
    onClick: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.height(60.dp),
        onClick = {
            onClick(toggle)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = toggle.containerColor(),
            ),
    ) {
        Text(toggle.optionName)
    }
}
