package com.wooyj.ordermenu.ui.screen.common.button.option

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun OptionGroupTitle(
    uiState: OptionGroupTitleUiState,
    onClick: (OptionButtonUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isVisible) {
        Column(modifier = modifier) {
            Text(text = stringResource(id = uiState.title))
            OptionButtonGroup(uiState = uiState.optionGroupUiState.list, onClick = onClick)
        }
    }
}

@Composable
fun OptionButtonGroup(
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
            OptionButton(
                uiState = it,
                onClick = onClick,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(4.dp),
            )
        }
    }
}
