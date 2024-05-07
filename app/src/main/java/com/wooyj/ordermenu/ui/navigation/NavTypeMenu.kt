package com.wooyj.ordermenu.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.GsonBuilder
import com.wooyj.ordermenu.data.MenuType
import org.json.JSONObject
import java.lang.IllegalArgumentException

class NavTypeMenu : NavType<MenuType>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MenuType? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): MenuType {
        println("parse value log>>>>>>>>>>>>>>>>>> $value")
        val menuName = JSONObject(value).getString("menuName")
        val typeClass = when (menuName) {
            in listOf("아메리카노", "카페라떼", "카푸치노") -> {
                MenuType.Coffee::class.java
            }

            in listOf("오렌지에이드", "망고에이드") -> {
                MenuType.Beverage::class.java
            }

            in listOf("얼그레이티", "페퍼민트티") -> {
                MenuType.Tea::class.java
            }

            in listOf("치즈케이크", "초코케이크", "마들렌", "휘낭시에") -> {
                MenuType.Dessert::class.java
            }
            else -> throw IllegalArgumentException("카테고리가 없는 메뉴이름입니다.")
        }
        return GsonBuilder().create().fromJson(value, typeClass)

    }

    override fun put(bundle: Bundle, key: String, value: MenuType) {
        return bundle.putParcelable(key, value)
    }

}