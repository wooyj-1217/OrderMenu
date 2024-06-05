package com.wooyj.ordermenu.data.option

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
