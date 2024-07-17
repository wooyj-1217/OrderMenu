# 1. Application Context를 멤버변수로 가지고 있을 경우 어떤 위험성이 있나요?
> Memory Leak이 발생할 수 있습니다.
> Application Context는 앱이 종료되기 전까지 살아있는데, 이를 가지고 있는 객체가 살아있는 동안 계속 참조하고 있으면 GC가 수거하지 못하고 메모리 누수가 발생할 수 있습니다.
## GC
> 원리 : 안쓰는 객체를 수거한다. -> 안쓰는지 어떻게 알아요??? -> refCount == 0
> Strong Ref
> Weak Ref

# 2. 4대 컴포넌트들의 Context가 각각 다른 역할이 있다고 하는데 보통 이 4가지를 사용할 때 보면 뭉뚱그러서 Context로 적고 call 하잖아요. 내부에서 알아서 구분을 해줘서 뭉뚱그러서 부르는건가요?
> 네

# 3. Toast는 왜 Application Context도 받는건가요?(앱이 닫혀도 토스트 메시지가 띄워져야 하는 때가 있는걸까요?
>  OS 에서 유일한 소통 창구

# 4. XML에서 Constraint Layout 쓰는 것은 괜찮은건가요?
> XML -> 3가지(Frame, Linear, Constraint)
> Frame > ScrollView > Constraint
> Linear > Appbar : 56dp, Body  weight: 1 -> Frame > ScrollView > Constraint

# 5. 기존의 앱보다 성능이 올라갔다거나 내려갔다는 것을 측정하는 도구는 어떤것을 추천하시나요?
> 오늘 수업

# 6. Android Dev -> Use iPhone ??? 배신자, 너는 뭐냐, .....
> 꼰대 마인드 -> 왜 니앱을 니가 안쓰냐??
 
# 7. 네이티브 개발자의 미래..? 매번 네이티브로 회귀한다고는 하지만 크로스플랫폼은 계속나오고.. 계속 네이티브여도 되는건지..ㅠㅠ?
> Native -> Android(Kotlin, Java), iOS(Swift)
> Cross  -> Flutter(Dart), ReactNative(React, JavaScript -> TypeScript)
> Hybrid -> Kotlin - KMM, KMP
> WebView 중간에 들어가는건 Basic
---
> WebApp -> WebView만 있는 앱 -> Native 개발자 필요 없음 

# 8. 웹 하시는 분들은 저희가 하고있는 아키텍처와 디자인패턴 없이 작업하나요
스터디원이 풀스택이긴 한데 프론트는 웹만 해봤고, 저랑 플러터 같이 공부하시는데 화면에 데이터 코드 다넣고 하시다가 
제가 상태관리 해야된다 하고 MVVM이랑 클린 아키텍처 자료 보내주니까 왜 이렇게 해야되는지 모르시겠다고 하셔요.

> System 차이......
> 클린 아키는 하는게 맞아요