package com.wooyj.ordermenu.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


enum class TempOption {
    Hot, Ice;
}

sealed class MenuType : Parcelable {

    abstract val menuName: String
    abstract val price: Int

    @Parcelize
    class Coffee(
        override val menuName: String,
        override val price: Int,
        val temperature: List<TempOption>,
        val hasCaffeine: Boolean
    ) : MenuType()


    @Parcelize
    class Beverage(
        override val menuName: String,
        override val price: Int,
        val temperature: List<TempOption>,
    ) : MenuType()


    @Parcelize
    class Tea(
        override val menuName: String,
        override val price: Int,
        val hasCaffeine: Boolean
    ) : MenuType()

    @Parcelize
    class Dessert(
        override val menuName: String,
        override val price: Int,
    ) : MenuType()
}


val menuList = listOf(
    MenuType.Coffee("아메리카노", 1000, listOf(TempOption.Hot, TempOption.Ice), true),
    MenuType.Coffee("카페라떼", 1500, listOf(TempOption.Hot, TempOption.Ice), true),
    MenuType.Coffee("카푸치노", 2000, listOf(TempOption.Hot, TempOption.Ice), true),

    MenuType.Beverage(
        "오렌지에이드", 2500, listOf(TempOption.Ice),
    ),
    MenuType.Beverage(
        "망고에이드", 2500, listOf(TempOption.Ice),
    ),

    MenuType.Tea("얼그레이티", 1000, true),
    MenuType.Tea("페퍼민트티", 1000, true),

    MenuType.Dessert("치즈케이크", 3000),
    MenuType.Dessert("초코케이크", 3000),
    MenuType.Dessert("마들렌", 1000),
    MenuType.Dessert("휘낭시에", 1500),
)