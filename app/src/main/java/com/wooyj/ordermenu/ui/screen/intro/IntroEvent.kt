package com.wooyj.ordermenu.ui.screen.intro

sealed class IntroEvent {
    data object OnNextClickEvent : IntroEvent()
}