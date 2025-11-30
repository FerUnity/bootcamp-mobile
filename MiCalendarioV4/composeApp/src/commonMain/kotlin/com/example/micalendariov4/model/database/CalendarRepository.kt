package com.example.micalendariov4.model.database

import com.example.micalendariov4.database.Holiday

class CalendarRepository(private val database: HolidayDatabase) {
    fun getHolidaysFromMonth(month: Long, year: Long, country: String) = database.taskQueries.selectHolidaysFromMonth(month, year, country).executeAsList()
    fun getHoliday(id: Long) = database.taskQueries.selectHoliday(id).executeAsOne()
    fun insertHoliday(holiday: Holiday) = database.taskQueries.insertHoliday(holiday.id, holiday.year, holiday.month, holiday.day, holiday.description, holiday.country)
    fun removeHoliday(id: Long) = database.taskQueries.removeHoliday(id)
}