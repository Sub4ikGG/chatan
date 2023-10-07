package ru.chatan.app

import java.util.Calendar
import java.util.TimeZone

fun Long.getCalendarTime(): String {
    val c = Calendar.getInstance(TimeZone.getDefault())
    c.timeInMillis = this * 1000

    val hour = c[Calendar.HOUR_OF_DAY].toCalendar()
    val minute = c[Calendar.MINUTE].toCalendar()

    return "$hour:$minute"
}

fun Int.toCalendar(): String =
    if (this > 9) this.toString() else "0$this"