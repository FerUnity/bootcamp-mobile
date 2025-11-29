package com.example.micalendario.calendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number


data class DayCell(
    val date: LocalDate?,
    val isToday: Boolean,
    val isSunday: Boolean,
    val isHoliday: Boolean
)

/**
 * Construye las filas (semanas) del calendario del mes.
 */
fun buildMonthCells(
    monthStart: LocalDate,
    holidays: Set<LocalDate>,
    today: LocalDate
): List<List<DayCell>> {
    val daysInMonth = daysInMonth(monthStart.year, monthStart.month.number)
    val firstDayOfWeek = dayOfWeekIndex(monthStart.dayOfWeek) // 1 = Lunes ... 7 = Domingo

    val cells = mutableListOf<DayCell>()

    // Huecos antes del día 1
    repeat(firstDayOfWeek - 1) {
        cells.add(
            DayCell(
                date = null,
                isToday = false,
                isSunday = false,
                isHoliday = false
            )
        )
    }

    // Días reales del mes
    for (day in 1..daysInMonth) {
        val date = LocalDate(monthStart.year, monthStart.month.number, day)
        val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
        val isHoliday = holidays.contains(date)
        val isToday = date == today

        cells.add(
            DayCell(
                date = date,
                isToday = isToday,
                isSunday = isSunday,
                isHoliday = isHoliday
            )
        )
    }

    // Rellenar hasta múltiplo de 7
    while (cells.size % 7 != 0) {
        cells.add(
            DayCell(
                date = null,
                isToday = false,
                isSunday = false,
                isHoliday = false
            )
        )
    }

    // Agrupar en filas de 7 (semanas)
    return cells.chunked(7)
}

/**
 * Cantidad de días del mes (monthNumber: 1..12)
 */
fun daysInMonth(year: Int, monthNumber: Int): Int =
    when (monthNumber) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> error("Mes inválido: $monthNumber")
    }

/**
 * Lunes = 1 ... Domingo = 7
 */
private fun dayOfWeekIndex(dow: DayOfWeek): Int =
    when (dow) {
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        DayOfWeek.SUNDAY -> 7
    }

private fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
