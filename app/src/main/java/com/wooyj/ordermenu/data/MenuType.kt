package com.wooyj.ordermenu.data

import android.util.Log
import com.wooyj.ordermenu.data.option.CaffeineOption
import com.wooyj.ordermenu.data.option.TempOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.NumberFormat
import java.util.Locale

@Serializable
@JvmInline
value class Price(val value: Int) {
    init {
        Log.d("Price", "$value")
        require(value > 0) { "값은 항상 0보다 커야 합니다." }
    }

    override fun toString(): String {
        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        return format.format(value)
    }
}

// VO(Value Object)
@Serializable
sealed class MenuType(
    open val id: Int,
    open val menuName: String,
    open val price: Price,
    val listTempOption: List<TempOption> = emptyList(),
    val listCaffeineOption: List<CaffeineOption> = emptyList(),
) {
    @Serializable
    class Coffee(
        @SerialName("coffeeMenuName") override val menuName: String,
        @SerialName("coffeeMenuPrice") override val price: Price,
        @SerialName("coffeeId") override val id: Int,
    ) : MenuType(
            id = id,
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot, TempOption.Ice),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        )

    @Serializable
    class Beverage(
        @SerialName("beverageMenuName") override val menuName: String,
        @SerialName("beverageMenuPrice") override val price: Price,
        @SerialName("beverageId") override val id: Int,
    ) : MenuType(
            id = id,
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Ice),
        )

    @Serializable
    class Tea(
        @SerialName("teaMenuName") override val menuName: String,
        @SerialName("teaMenuPrice") override val price: Price,
        @SerialName("teaId") override val id: Int,
    ) : MenuType(
            id = id,
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        )

    @Serializable
    class Dessert(
        @SerialName("dessertMenuName") override val menuName: String,
        @SerialName("dessertMenuPrice") override val price: Price,
        @SerialName("dessertId") override val id: Int,
    ) : MenuType(
            id = id,
            menuName = menuName,
            price = price,
        )
}

val menuList =
    listOf(
        MenuType.Coffee(menuName = "아메리카노", price = Price(1000), id = 1),
        MenuType.Coffee(menuName = "카페라떼", price = Price(1500), id = 2),
        MenuType.Coffee(menuName = "카푸치노", price = Price(2000), id = 3),
        MenuType.Beverage(menuName = "오렌지에이드", price = Price(2500), id = 4),
        MenuType.Beverage(menuName = "망고에이드", price = Price(2500), id = 5),
        MenuType.Tea(menuName = "얼그레이티", price = Price(1000), id = 6),
        MenuType.Tea(menuName = "페퍼민트티", price = Price(1000), id = 7),
        MenuType.Dessert(menuName = "치즈케이크", price = Price(3000), id = 8),
        MenuType.Dessert(menuName = "초코케이크", price = Price(3000), id = 9),
        MenuType.Dessert(menuName = "마들렌", price = Price(1000), id = 10),
        MenuType.Dessert(menuName = "휘낭시에", price = Price(1500), id = 11),
    )

fun Int.menuTypeFromId(): MenuType = menuList.first { it.id == this }

fun String.menuIdFromMenuName(): Int = menuList.first { it.menuName == this }.id
