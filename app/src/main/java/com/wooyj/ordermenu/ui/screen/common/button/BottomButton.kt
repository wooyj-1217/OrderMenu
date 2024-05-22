package com.wooyj.ordermenu.ui.screen.common.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun BottomButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(16.dp),
        onClick = onClick,
    ) {
        Text(text = text)
    }
}

@Composable
private fun BottomButton(
    uiState: BottomButtonUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BottomButton(
        text = uiState.text(),
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BottomButton(
        uiState = BottomButtonUiState.Next,
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BottomButton(
        uiState = BottomButtonUiState.Close,
        modifier = modifier,
        onClick = onClick,
    )
}
