package com.wooyj.ordermenu.utils

import java.text.NumberFormat
import java.util.Locale

fun Int.addCommasToNumber(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault())
    return format.format(this)
}
