package com.wooyj.ordermenu

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OrderMenuApp : Application()

// TODO("1. Event/Effect 좀 헷갈려요.")

/**
 *
 *  ex) Infinite Scroll 구현
 *  1. Scroll -> Event
 *  2. Scroll 위치가 최하단인지를 감지 -> 감지하는 순간부터 Effect..? 아니면 Event...?
 *     2-1. Scroll 위치가 최하단일 경우 : API 호출 -> Effect
 *     2-2. Scroll 위치가 최하단이 아닐 경우 : 아무것도 하지 않음 -> Effect
 */

// TODO("2. SnackbarHostState와 같은 State도 Screen을 관리하는 State에서 관리하나요?")

/**
 * ex)
 * UI(
 *   val uiState: UiState,
 *   val snackbarHostState: SnackbarHostState,
 *   val list: List<ListModel>,
 * )
 *
 */

// TODO("3. 현업에서 SharedFlow를 쓰는 경우는 어떤게 있나요?")
