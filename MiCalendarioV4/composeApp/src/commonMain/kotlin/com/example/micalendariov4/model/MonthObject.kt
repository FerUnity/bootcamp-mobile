package com.example.micalendariov4.model

import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

data class MonthObject(
    val month: Month,
    val year: Int
) {
    // Día de la semana en que comienza el mes
    fun getStartDayOfWeek(): Int {
        val firstDay =  LocalDate.of(year, month, 1)
        return firstDay.dayOfWeek.value
    }

    // Número de días del mes
    fun getDaysInMonth(): Int {
        return LocalDate.of(year, month, 1).lengthOfMonth()
    }

    // Número de semanas del mes
    fun getWeeksInMonth(): Int {
        return 5
        // return (getDaysInMonth() + getFirstDayOfWeek()) / 7
    }

    // Nombre del mes en el idioma y país especificado
    fun getLocaleMonth(language: String, country: String): String {
        val localeEs = Locale(language, country)
        return month.getDisplayName(TextStyle.FULL, localeEs)
    }

    // Día de la semana del primer día del mes
    fun getFirstDayOfWeek(language: String, country: String): Int {
        val firstDay =  LocalDate.of(year, month, 1)
        val weekFields = WeekFields.of(Locale(language, country))
        return firstDay.get(weekFields.dayOfWeek())
    }
}
