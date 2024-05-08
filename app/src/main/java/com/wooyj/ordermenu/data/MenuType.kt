package com.wooyj.ordermenu.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class TempOption {
    Hot,
    Ice,
    ;

    override fun toString(): String {
        return when (this) {
            Hot -> "HOT"
            Ice -> "ICE"
        }
    }
}

enum class CaffeineOption {
    Caffeine,
    Decaffeine,
    ;

    override fun toString(): String {
        return when (this) {
            Caffeine -> "카페인"
            Decaffeine -> "디카페인"
        }
    }
}

enum class IceOption {
    Small,
    Medium,
    Large,
    ;

    override fun toString(): String {
        return when (this) {
            Small -> "적게"
            Medium -> "보통"
            Large -> "많이"
        }
    }
}

@Parcelize
@JvmInline
value class Price(val value: Int) : Parcelable {
    init {
        require(value > 0) { "Price must be positive" }
    }
}

data class MenuSelect(
    val menuType: MenuType,
    val tempOption: TempOption?,
    val caffeineOption: CaffeineOption?,
    val iceOption: IceOption?,
) {
    override fun toString(): String {
        val description = mutableListOf<String>()
        description.add(menuType.menuName)
        tempOption?.let { description.add(it.toString()) }
        caffeineOption?.let { description.add(it.toString()) }
        iceOption?.let { description.add(it.toString()) }
        return description.joinToString("/")
    }
}

sealed class MenuType(
    open val menuName: String,
    open val price: Price,
    open val tempOption: List<TempOption> = emptyList(),
    open val caffeineOption: List<CaffeineOption> = emptyList(),
) : Parcelable {
    @Parcelize
    class Coffee(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
            menuName,
            price,
            listOf(TempOption.Hot, TempOption.Ice),
            listOf(CaffeineOption.Caffeine, CaffeineOption.Decaffeine),
        )

    @Parcelize
    class Beverage(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(menuName, price, listOf(TempOption.Ice))

    @Parcelize
    class Tea(
        override val menuName: String,
        override val price: Int,
        val hasCaffeine: Boolean,
    ) : MenuType()

    @Parcelize
    class Dessert(
        override val menuName: String,
        override val price: Int,
    ) : MenuType()
}

val menuList =
    listOf(
        MenuType.Coffee("아메리카노", 1000, listOf(TempOption.Hot, TempOption.Ice), true),
        MenuType.Coffee("카페라떼", 1500, listOf(TempOption.Hot, TempOption.Ice), true),
        MenuType.Coffee("카푸치노", 2000, listOf(TempOption.Hot, TempOption.Ice), true),
        MenuType.Beverage(
            "오렌지에이드",
            2500,
            listOf(TempOption.Ice),
        ),
        MenuType.Beverage(
            "망고에이드",
            2500,
            listOf(TempOption.Ice),
        ),
        MenuType.Tea("얼그레이티", 1000, true),
        MenuType.Tea("페퍼민트티", 1000, true),
        MenuType.Dessert("치즈케이크", 3000),
        MenuType.Dessert("초코케이크", 3000),
        MenuType.Dessert("마들렌", 1000),
        MenuType.Dessert("휘낭시에", 1500),
    )
