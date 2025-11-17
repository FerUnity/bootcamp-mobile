package com.example.micalendario.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.micalendario.AppDatabaseExample

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:app.db")
        AppDatabaseExample.Companion.Schema.create(driver)
        return driver
    }
}