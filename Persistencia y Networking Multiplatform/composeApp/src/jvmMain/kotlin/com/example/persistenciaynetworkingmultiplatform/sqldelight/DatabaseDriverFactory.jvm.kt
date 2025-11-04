package com.example.persistenciaynetworkingmultiplatform.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.persistenciaynetworkingmultiplatform.AppDatabaseExample

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:app.db")
        AppDatabaseExample.Companion.Schema.create(driver)
        return driver

    }
}