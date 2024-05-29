package com.wooyj.ordermenu.ui.screen.common.togglebutton

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    toggle: ToggleButtonUiState,
    onClick: (ToggleButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.height(60.dp),
        onClick = {
            onClick(toggle)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (toggle.isSelected) Color.DarkGray else Color.LightGray,
            ),
    ) {
        Text(toggle.optionName)
    }
}
