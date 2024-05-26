package com.wooyj.ordermenu

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// TODO("회사 관련 질문")

/**
 *  1. 배경
 *  - firebase cloud messaging으로 푸시알람을 생성하는데 백그라운드 상황에서 Big Image가 안나오는 현상 보고됨. hotfix로 작업해주길 바랐음.
 *  - 원인을 찾아보니 background 상황일 땐 onMessageReceived()로 넘어가지 않음. 시스템에서 처리.
 *    (페이로드 쪽에 "notification" key 쓰고있고, 백엔드에 notification key 값 문의해보니 고치면 iOS쪽에도 영향미치는거 아니냐는..그런 반응)
 *  - A 작업 : handleIntent()를 override해서 "gcm.notification.*" 과 같은 key 값을 *로 변경해서 다시 intent에 실음
 *            onMessageReceived() 불러오고, push alarm 제대로 형성되는 것 확인.
 *            배포했다가 문제생김 > 롤백함
 *            - Fatal Exception: java.net.UnknownHostException Unable to resolve host "{호스트명}": No address associated with hostname
 *            - 서버문제인지 물어봤더니 iOS 푸시알람 이미지는 문제없이 잘받아왔다고 아니라고 함.
 *  - B 작업 : handleIntent()를 override해서 받아온 intent를 RemoteMessage.builder로 쌓은 다음에 onMessageReceived() 불러오는 코드 작성
 *
 *  2. 질문
 *   - A 작업에서 문제가 왜 발생한 건가요?
 *   - B 작업에서 handleIntent() override 해서 onMessageReceived() 불러오는 코드, 정상적인 방법인가요?
 *   - 해결방법은 무엇일까요?
 *
 */

@HiltAndroidApp
class OrderMenuApp : Application()
