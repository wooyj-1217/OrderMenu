package com.wooyj.ordermenu.data.option

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

    companion object {
        fun fromString(string: String): IceOption =
            when (string) {
                Small.toString() -> Small
                Medium.toString() -> Medium
                Large.toString() -> Large
                else -> throw IllegalArgumentException("Unknown IceOption")
            }
    }
}
