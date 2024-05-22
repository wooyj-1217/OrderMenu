package com.wooyj.ordermenu.ui.screen.intro.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wooyj.ordermenu.ui.screen.common.button.NextButton

@Composable
fun IntroUI(
    onNextNavigation: () -> Unit,
    titleText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier =
                Modifier.padding(
                    start = 16.dp,
                    top = 80.dp,
                ),
        ) {
            Text(
                text = titleText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
            )
        }
        // Common => NextButton
        NextButton { onNextNavigation() }
    }
}
