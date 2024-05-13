package com.wooyj.ordermenu.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.Locale


enum class TempOption {
    Hot, Ice;

    override fun toString() = when (this) {
        Hot -> "HOT"
        Ice -> "ICE"
    }
}

enum class CaffeineOption {
    Caffeine, DeCaffeine;

    override fun toString(): String = when (this) {
        Caffeine -> "카페인"
        DeCaffeine -> "디카페인"
    }
}

enum class IceOption {
    Small,
    Medium,
    Large;

    override fun toString(): String = when (this) {
        Small -> "적게"
        Medium -> "보통"
        Large -> "많이"
    }
}

@Parcelize
@JvmInline
value class Price(val value: Int) : Parcelable {
    init {
        require(value > 0) { "값은 항상 0보다 커야 합니다." }
    }

    fun Price.addCommasToNumber(): String {
        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        return format.format(this)
    }
}


// DTO(Data Transfer Object)
data class OrderOption(
    val menuType: MenuType,
    val tempOption: TempOption?,
    val caffeineOption: CaffeineOption?,
    val iceOption: IceOption?
){
    fun getOptionString(): String {
        val desc = mutableListOf<String>()
        tempOption?.let { desc.add(it.toString()) }
        caffeineOption?.let { desc.add(it.toString()) }
        iceOption?.let { desc.add("얼음($it)") }
        return super.toString()
    }
}


// VO(Value Object)
sealed class MenuType(
    open val menuName: String,
    open val price: Price,
    open val listTempOption: List<TempOption> = emptyList(),
    open val listCaffeineOption: List<CaffeineOption> = emptyList()
) : Parcelable {

    @Parcelize
    class Coffee(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
        menuName = menuName,
        price = price,
        listTempOption = listOf(TempOption.Hot, TempOption.Ice),
        listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine)
    )


    @Parcelize
    class Beverage(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(
        menuName = menuName,
        price = price,
        listTempOption = listOf(TempOption.Ice),
        listCaffeineOption = listOf(CaffeineOption.Caffeine, CaffeineOption.DeCaffeine)
    )


    @Parcelize
    class Tea(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(menuName = menuName, price = price, listTempOption = listOf(TempOption.Hot))

    @Parcelize
    class Dessert(
        override val menuName: String,
        override val price: Price,
    ) : MenuType(menuName = menuName, price = price)
}


val menuList = listOf(
    MenuType.Coffee("아메리카노", Price(1000)),
    MenuType.Coffee("카페라떼", Price(1500)),
    MenuType.Coffee("카푸치노", Price(2000)),
    MenuType.Beverage(
        "오렌지에이드", Price(2500)),

    MenuType.Beverage(
        "망고에이드", Price(2500)
    ),

    MenuType.Tea("얼그레이티", Price(1000)),
    MenuType.Tea("페퍼민트티", Price(1000)),

    MenuType.Dessert("치즈케이크", Price(3000)),
    MenuType.Dessert("초코케이크", Price(3000)),
    MenuType.Dessert("마들렌", Price(1000)),
    MenuType.Dessert("휘낭시에", Price(1500)),
)