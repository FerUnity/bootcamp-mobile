package com.example.micalendariov4.model.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.micalendariov4.database.CalendarDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:calendar.db")
        CalendarDatabase.Schema.create(driver)
        return driver
    }
}