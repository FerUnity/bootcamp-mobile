package com.example.micalendariov4.model.database

import com.example.micalendariov4.database.Holiday
import java.time.Month

class CalendarRepository(private val database: HolidayDatabase) {
    fun getHoliday(id: Long) = database.taskQueries.selectHolidayFromId(id).executeAsOne()
    fun getHolidaysFromDate(year: Long, month: Long, day: Long) = database.taskQueries.selectHolidayFromDate(year, month, day).executeAsList()
    fun getHolidaysFromMonth(month: Month, year: Long, country: String) = database.taskQueries.selectHolidaysFromMonth(month.value.toLong(), year, country).executeAsList()
    fun insertHoliday(holiday: Holiday) = database.taskQueries.insertHoliday(holiday.year, holiday.month, holiday.day, holiday.description, holiday.country)
    fun removeHoliday(id: Long) = database.taskQueries.removeHoliday(id)
}