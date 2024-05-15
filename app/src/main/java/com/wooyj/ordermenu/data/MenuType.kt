package com.wooyj.ordermenu.data

import android.util.Log
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
        require(value > 0) { "값은 항상 0보다 커야 합니다." }
        Log.d("Price", "$value")
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
    open val menuName: String,
    open val price: Price,
    val listTempOption: List<TempOption> = emptyList(),
    val listCaffeineOption: List<CaffeineOption> = emptyList(),
    val listIceOption: List<IceOption> = emptyList(),
) {
    // 넘기는 법 관련 android developer 사이트 설명
    // https://developer.android.com/guide/navigation/design/kotlin-dsl#custom-types

    // error : Serializable class has duplicate serial name of property 'menuName', either in the class itself or its supertypes
    // @Transient(=temporary) : 상위속성이 중복되지 않도록 하기 위한 annotation. 무조건 기본값을 셋팅해주긴 해야 함.

    @Serializable
    class Coffee(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot, TempOption.Ice),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
            listIceOption = listOf(IceOption.Small, IceOption.Medium, IceOption.Large),
        )

    @Serializable
    class Beverage(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Ice),
            listIceOption = listOf(IceOption.Small, IceOption.Medium, IceOption.Large),
        )

    @Serializable
    class Tea(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
            menuName = menuName,
            price = price,
            listTempOption = listOf(TempOption.Hot),
            listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine),
        )

    @Serializable
    class Dessert(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(menuName = menuName, price = price)
}

// error : kotlinx.serialization.SerializationException: Serializer for class 'MenuType' is not found.
//         Please ensure that class is marked as '@Serializable' and that the serialization compiler plugin is applied.
// 해당 에러 관련해서 sealed class는 serialize를 자동변환해주지 않는건가 하고 찾아보니 이런게 있긴 함.
// https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-core/kotlinx.serialization/-sealed-class-serializer/
// https://stackoverflow.com/questions/74550841/kotlin-serialization-of-value-class-that-implements-a-sealed-interface

val menuList =
    listOf(
        MenuType.Coffee("아메리카노", Price(1000)),
        MenuType.Coffee("카페라떼", Price(1500)),
        MenuType.Coffee("카푸치노", Price(2000)),
        MenuType.Beverage("오렌지에이드", Price(2500)),
        MenuType.Beverage("망고에이드", Price(2500)),
        MenuType.Tea("얼그레이티", Price(1000)),
        MenuType.Tea("페퍼민트티", Price(1000)),
        MenuType.Dessert("치즈케이크", Price(3000)),
        MenuType.Dessert("초코케이크", Price(3000)),
        MenuType.Dessert("마들렌", Price(1000)),
        MenuType.Dessert("휘낭시에", Price(1500)),
    )
