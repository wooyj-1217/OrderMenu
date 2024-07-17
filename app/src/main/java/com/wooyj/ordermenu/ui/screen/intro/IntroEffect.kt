package com.wooyj.ordermenu.ui.screen.intro

sealed interface IntroEffect {
    data object NavigateToMenuList : IntroEffect
}
