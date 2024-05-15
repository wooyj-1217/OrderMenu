package com.wooyj.ordermenu.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavType
import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.OrderOption
import com.wooyj.ordermenu.data.menuTypeJson
import com.wooyj.ordermenu.data.orderOptionJson
import kotlinx.serialization.json.Json

val NavTypeMenu =
    object : NavType<MenuType>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): MenuType? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, MenuType::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): MenuType {
            return menuTypeJson.decodeFromString(MenuType.serializer(), value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: MenuType,
        ) {
            return bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: MenuType): String {
            return menuTypeJson.encodeToString(MenuType.serializer(), value)
        }
    }

val NavTypeOrderOption =
    object : NavType<OrderOption>(isNullableAllowed = true) {
        // error >> java.lang.IllegalArgumentException: Cannot cast menuType of type com.wooyj.ordermenu.data.MenuType to a NavType.
        //         Make sure to provide custom NavType for this argument.
        // OrderOption 내에도 MenuType이 있기 때문에 생긴 이슈.

        override fun get(
            bundle: Bundle,
            key: String,
        ): OrderOption? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, OrderOption::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): OrderOption {
            Log.d("parseValue", value)

            // 여기서 발생한 이슈인데 java.lang.IllegalArgumentException: Navigation destination that matches request NavDeepLinkRequest가 뜨는구나?;;;
//        val result = Json.decodeFromString(OrderOption.serializer(), value)
            val decoded = Uri.decode(value)
            Log.d("uriDecode", "$decoded")
            Log.d("parseValue/price", "${orderOptionJson.decodeFromString(OrderOption.serializer(), decoded)}")

//        return Json.decodeFromString<OrderOption>(value)
            // 이렇게 넘겼을때도 java.lang.IllegalArgumentException: Navigation destination that matches request NavDeepLinkRequest가 뜨는건 머선일..?
//        return OrderOption(
//            MenuType.Coffee("아메리카노", Price(1000)),
//            tempOption = TempOption.Hot,
//            caffeineOption = CaffeineOption.Caffeine,
//            iceOption = IceOption.Small
//        )
            return orderOptionJson.decodeFromString(OrderOption.serializer(), decoded)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: OrderOption,
        ) {
            Log.d("put", "$value")
            return bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: OrderOption): String {
            Log.d("NavTypeOrderOption", Json.encodeToString(OrderOption.serializer(), value))
            return orderOptionJson.encodeToString(OrderOption.serializer(), value)
        }
    }

// var NavTypeTempOption = object: NavType<TempOption?>(isNullableAllowed = true) {
//    override fun get(bundle: Bundle, key: String): TempOption? {
//        return bundle.getString(key)?.let { TempOption.valueOf(it) }
//    }
//
//    override fun parseValue(value: String): TempOption? {
//        return TempOption.valueOf(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: TempOption?) {
//        bundle.putString(key, value?.name)
//    }
// }

// enum class 공통 클래스로 만들 수 있지않나.

inline fun <reified T : Enum<T>> NavTypeEnum(isNullableAllowed: Boolean = true) =
    object : NavType<T>(isNullableAllowed) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): T? {
            return bundle.getString(key)?.let { java.lang.Enum.valueOf(T::class.java, it) }
        }

        // TODO("대체 왜 java.lang.을 때면 valueOf() function을 못찾는걸까요..?")
        override fun parseValue(value: String): T {
            return java.lang.Enum.valueOf(T::class.java, value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: T,
        ) {
            bundle.putString(key, value.name)
        }
    }
