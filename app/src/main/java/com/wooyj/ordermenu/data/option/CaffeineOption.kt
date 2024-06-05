package com.wooyj.ordermenu.data.option

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
