package com.example.thecr.util

object CalendarHelper {
    fun dayOfWeek(dayInt: Int): String = when (dayInt) {
        0 -> "MONDAY"
        1 -> "TUESDAY"
        2 -> "WEDNESDAY"
        3 -> "THURSDAY"
        4 -> "FRIDAY"
        else -> {
            "NO"
        }
    }

    fun getDayNumberSuffix(day: Int): String {
        return if (day in 11..13) {
            "th"
        } else when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}