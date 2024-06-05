package com.wooyj.ordermenu.ui.screen.common.button.bottom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun BottomButton(
    onNextNavigation: () -> Unit,
    stringId: Int,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier =
            modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(16.dp),
        onClick = onNextNavigation,
    ) {
        Text(
            text = stringResource(id = stringId),
            fontSize = 20.sp,
        )
    }
}

@Composable
private fun BottomButton(
    onNextNavigation: () -> Unit,
    uiState: BottomButtonUiState,
    modifier: Modifier = Modifier,
) {
    BottomButton(
        onNextNavigation = onNextNavigation,
        stringId = uiState.text(),
        modifier = modifier,
    )
}

@Composable
fun NextButton(
    onNextNavigation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomButton(
        onNextNavigation = onNextNavigation,
        uiState = BottomButtonUiState.Next,
        modifier = modifier,
    )
}

@Composable
fun CloseButton(
    onNextNavigation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomButton(
        onNextNavigation = onNextNavigation,
        uiState = BottomButtonUiState.Close,
        modifier = modifier,
    )
}
