package com.wooyj.ordermenu

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wooyj.ordermenu.domain.repository.DogRepository
import com.wooyj.ordermenu.ui.navigation.OrderMenuNavHost
import com.wooyj.ordermenu.ui.theme.OrderMenuTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    val ctx: Context = this
    val ctx: WeakReference<Context> = WeakReference(this)

    @Inject
    lateinit var repository: DogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ctx.get()?.let {
        }

        setContent {
            OrderMenuTheme {
                OrderMenuNavHost()
            }
        }
    }
}

// TODO("Application Context를 멤버변수로 가지고 있을 경우 어떤 위험성이 있나요?")
// TODO("4대 컴포넌트들의 Context가 각각 다른 역할이 있다고 하는데 보통 이 4가지를 사용할 때 보면 뭉뚱그러서 Context로 적고 call 하잖아요.
//       내부에서 알아서 구분을 해줘서 뭉뚱그러서 부르는건가요?")
// TODO("Toast는 왜 Application Context도 받는건가요?(앱이 닫혀도 토스트 메시지가 띄워져야 하는 때가 있는걸까요?)")
// TODO("XML에서 Constraint Layout 쓰는 것은 괜찮은건가요?")

// TODO("기존의 앱보다 성능이 올라갔다거나 내려갔다는 것을 측정하는 도구는 어떤것을 추천하시나요?")
// TODO("안드로이드 개발자면 아이폰 쓰면 안되나요?
//      예전에 한 회사 면접에서 왜 안드로이드 개발자면서 아이폰 쓴다고 하니까 자긴 안좋게본다고 바로 말하시더라구요
//      이유는 안드로이드 폰 안쓰면서 개발을 어떻게하냐는 이유였음.
//      (전 개발용 휴대폰 구글 픽셀폰으로 따로 사서 쓰고있습니다로 대답하긴 함.)")
// TODO("네이티브 개발자의 미래..? 매번 네이티브로 회귀한다고는 하지만 크로스플랫폼은 계속나오고.. 계속 네이티브여도 되는건지..ㅠㅠ?")
// TODO("웹 하시는 분들은 저희가 하고있는 아키텍처와 디자인패턴 없이 작업하나요?
//       스터디원이 풀스택이긴 한데 프론트는 웹만 해봤고,
//       저랑 플러터 같이 공부하시는데 화면에 데이터 코드 다넣고 하시다가
//       제가 상태관리 해야된다 하고 MVVM이랑 클린 아키텍처 자료 보내주니까 왜 이렇게 해야되는지 모르시겠다고 하셔요.")
