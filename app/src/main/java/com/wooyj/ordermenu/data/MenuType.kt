package com.wooyj.ordermenu.data

import android.content.res.Resources
import android.util.Log
import com.wooyj.ordermenu.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.NumberFormat
import java.util.Locale

enum class TempOption {
    Hot,
    Ice,
    ;

    override fun toString() =
        when (this) {
            Hot -> "HOT"
            Ice -> "ICE"
        }

    companion object {
        fun fromString(string: String): TempOption =
            when (string) {
                Hot.toString() -> Hot
                Ice.toString() -> Ice
                else -> throw IllegalArgumentException("Unknown TempOption")
            }
    }
}

enum class CaffeineOption {
    Caffeine,
    DeCaffeine,
    ;

    override fun toString(): String =
        when (this) {
            Caffeine -> "카페인"
            DeCaffeine -> "디카페인"
        }

    companion object {
        fun fromString(string: String): CaffeineOption =
            when (string) {
                Caffeine.toString() -> Caffeine
                DeCaffeine.toString() -> DeCaffeine
                else -> throw IllegalArgumentException("Unknown CaffeineOption")
            }
    }
}

enum class IceOption {
    Small,
    Medium,
    Large,
    ;

    override fun toString(): String =
        when (this) {
            Small -> "적게"
            Medium -> "보통"
            Large -> "많이"
        }

    companion object {
        fun fromString(string: String): IceOption =
            when (string) {
                Small.toString() -> Small
                Medium.toString() -> Medium
                Large.toString() -> Large
                else -> throw IllegalArgumentException("Unknown IceOption")
            }
    }
}

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

// DTO(Data Transfer Object)
@Serializable
data class OrderOption(
    val menuType: MenuType,
    var tempOption: TempOption?,
    var caffeineOption: CaffeineOption?,
    var iceOption: IceOption?,
) {
    override fun toString(): String {
        val desc = mutableListOf<String>()
        tempOption?.let { desc.add(it.toString()) }
        caffeineOption?.let { desc.add(it.toString()) }
        iceOption?.let { desc.add("얼음($it)") }
        return desc.joinToString("/")
    }
}

// VO(Value Object)
@Serializable
sealed class MenuType(
    // id 추가
    open val menuName: String,
    open val price: Price,
    val listTempOption: List<TempOption> = emptyList(),
    val listCaffeineOption: List<CaffeineOption> = emptyList(),
) {
    @Serializable
    class Coffee(
        @SerialName("coffeeMenuName") override val menuName: String,
        @SerialName("coffeeMenuPrice") override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot, TempOption.Ice),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        ) {
        override fun toString(): String {
            return Resources.getSystem().getString(R.string.coffee)
        }
    }

    @Serializable
    class Beverage(
        @SerialName("beverageMenuName") override val menuName: String,
        @SerialName("beverageMenuPrice") override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Ice),
        ) {
        override fun toString(): String {
            return Resources.getSystem().getString(R.string.beverage)
        }
    }

    @Serializable
    class Tea(
        @SerialName("teaMenuName") override val menuName: String,
        @SerialName("teaMenuPrice") override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        ) {
        override fun toString(): String {
            return Resources.getSystem().getString(R.string.tea)
        }
    }

    @Serializable
    class Dessert(
        @SerialName("dessertMenuName") override val menuName: String,
        @SerialName("dessertMenuPrice") override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
        ) {
        override fun toString(): String {
            return Resources.getSystem().getString(R.string.dessert)
        }
    }
}

val menuList =
    listOf(
        MenuType.Coffee(menuName = "아메리카노", price = Price(1000)),
        MenuType.Coffee(menuName = "카페라떼", price = Price(1500)),
        MenuType.Coffee(menuName = "카푸치노", price = Price(2000)),
        MenuType.Beverage(menuName = "오렌지에이드", price = Price(2500)),
        MenuType.Beverage(menuName = "망고에이드", price = Price(2500)),
        MenuType.Tea(menuName = "얼그레이티", price = Price(1000)),
        MenuType.Tea(menuName = "페퍼민트티", price = Price(1000)),
        MenuType.Dessert(menuName = "치즈케이크", price = Price(3000)),
        MenuType.Dessert(menuName = "초코케이크", price = Price(3000)),
        MenuType.Dessert(menuName = "마들렌", price = Price(1000)),
        MenuType.Dessert(menuName = "휘낭시에", price = Price(1500)),
    )
