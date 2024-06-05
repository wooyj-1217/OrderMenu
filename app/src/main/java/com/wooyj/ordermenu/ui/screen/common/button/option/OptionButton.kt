package com.wooyj.ordermenu.ui.screen.common.button.option

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OptionButton(
    uiState: OptionButtonUiState,
    modifier: Modifier = Modifier,
    onClick: (OptionButtonUiState) -> Unit,
) {
    Button(
        modifier = modifier.height(60.dp),
        onClick = {
            onClick(uiState)
        },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = uiState.containerColor(),
            ),
    ) {
        Text(uiState.optionName)
    }
}
