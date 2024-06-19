package com.wooyj.ordermenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wooyj.ordermenu.ui.navigation.OrderMenuNavHost
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO("firebase crashlytics를 slack 채널에 적용할 때 관련 질문입니다!")
// 1. firebase functions로 커스터마이징을 하나요? (https://firebase.google.com/docs/functions/beta/alert-events?hl=ko)
//    1-1. 만약 200만건까지 무료지만, 회사에서 유료로 사용하는게 안된다고 한다면 대안이 있을까요?
//    1-2. crashlytics에 보고되는 것 외에 어떤 부분에 대한 알림을 사용하면 유용할까요?
//    1-3. 서버 문제일 때 자동으로 보고되게 했다 하셨는데.. 이 부분은 어떻게 설정하나요?

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
