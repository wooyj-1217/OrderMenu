package com.wooyj.ordermenu.data.dto

import com.wooyj.ordermenu.data.MenuType
import com.wooyj.ordermenu.data.option.CaffeineOption
import com.wooyj.ordermenu.data.option.IceOption
import com.wooyj.ordermenu.data.option.TempOption
import kotlinx.serialization.Serializable

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
