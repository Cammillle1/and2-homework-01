package com.example.and2.homework.and.homework.s01.util

fun formatNumberShortPrecise(number: Long): String {
    return when {
        number < 10_000 -> {
            if (number < 1000) {
                number.toString()
            } else {
                val value = number.toDouble() / 1000
                "%.1fK".format(value)
            }
        }

        number < 1_000_000 -> {
            "${number / 1000}K"
        }

        else -> {
            val value = number.toDouble() / 1_000_000
            "%.1fM".format(value)
        }
    }
}