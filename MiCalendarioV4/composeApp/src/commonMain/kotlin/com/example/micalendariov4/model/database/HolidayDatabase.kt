package com.example.micalendariov4.model.database

import com.example.micalendariov4.database.CalendarDatabase

class HolidayDatabase(factory: DatabaseDriverFactory) {
    private val driver = factory.createDriver()
    private val database = CalendarDatabase(driver)

    val taskQueries = database.calendarDatabaseQueries
}