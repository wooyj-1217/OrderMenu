package com.wooyj.ordermenu.data

import android.util.Log
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
}

@Serializable
@JvmInline
value class Price(val value: Int) {
    init {
        Log.d("Price", "$value")
        require(value > 0) { "값은 항상 0보다 커야 합니다." }
    }

    fun addCommasToNumber(): String {
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
    open val menuName: String,
    open val price: Price,
    val listTempOption: List<TempOption> = emptyList(),
    val listCaffeineOption: List<CaffeineOption> = emptyList(),
    val listIceOption: List<IceOption> = emptyList(),
) {
    // error : Serializable class has duplicate serial name of property 'menuName', either in the class itself or its supertypes
    // - @Transient(=temporary) : 상위속성이 중복되지 않도록 하기 위한 annotation. 무조건 기본값을 셋팅해주긴 해야 함.
    // - 안되는 이유 : override 할 때 부모, 자식  두개의 백업 필드가 표시되기 때문. (저번주에 Price 값이 두번 바뀐 이유도 동일한 이슈였음. Transient로 기본값 Price(0)을 해놨으니까 2번 생성.)
    // https://github.com/Kotlin/kotlinx.serialization/issues/2000
    @Serializable
    class Coffee(
        @SerialName("coffeeMenuName") override val menuName: String,
        @SerialName("coffeeMenuPrice")override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot, TempOption.Ice),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
            listIceOption = listOf(IceOption.Small, IceOption.Medium, IceOption.Large),
        )

    @Serializable
    class Beverage(
        @SerialName("beverageMenuName") override val menuName: String,
        @SerialName("beverageMenuPrice") override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Ice),
            listIceOption = listOf(IceOption.Small, IceOption.Medium, IceOption.Large),
        )

    @Serializable
    class Tea(
        @SerialName("teaMenuName")override val menuName: String,
        @SerialName("teaMenuPrice")override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        )

    @Serializable
    class Dessert(
        @SerialName("dessertMenuName")override val menuName: String,
        @SerialName("dessertMenuPrice")override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
        )
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
