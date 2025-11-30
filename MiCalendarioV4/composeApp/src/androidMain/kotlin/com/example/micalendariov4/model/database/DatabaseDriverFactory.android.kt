package com.example.micalendariov4.model.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.micalendariov4.database.CalendarDatabase

actual class DatabaseDriverFactory(val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CalendarDatabase.Schema, context, "calendar.db")
    }
}