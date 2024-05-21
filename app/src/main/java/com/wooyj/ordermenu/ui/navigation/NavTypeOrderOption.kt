package com.wooyj.ordermenu.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.wooyj.ordermenu.data.OrderOption
import kotlinx.serialization.json.Json

val NavTypeOrderOption =
    object : NavType<OrderOption>(
        isNullableAllowed = false,
    ) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): OrderOption? {
            return bundle.getString(key)?.let {
                Json.decodeFromString(OrderOption.serializer(), it)
            }
        }

        override fun parseValue(value: String): OrderOption {
            return Json.decodeFromString(OrderOption.serializer(), value)
        }

        override fun serializeAsValue(value: OrderOption): String {
            return Json.encodeToString(OrderOption.serializer(), value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: OrderOption,
        ) {
            bundle.putString(key, Json.encodeToString(OrderOption.serializer(), value))
        }
    }
