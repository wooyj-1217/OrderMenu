package com.wooyj.ordermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wooyj.ordermenu.ui.navigation.OrderMenuNavHost
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO("파일 구조 관련 질문있습니다. 예를 들면, list > state > MenuListUI 가 있잖아요..
//     state 패키지 안에 composable ui가 있고.. uiState가 또 있으니까 state가 두개가 있는거 같아서
//     '어떻게 정리하는게 좋을까?, 혹은 헷갈리는 내 잘못인가..?' 여러모로 고민이 되는데 패키징 정리 어떻게 하는게 좋을까요?")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderMenuTheme {
                OrderMenuNavHost()
            }
        }
    }
}
