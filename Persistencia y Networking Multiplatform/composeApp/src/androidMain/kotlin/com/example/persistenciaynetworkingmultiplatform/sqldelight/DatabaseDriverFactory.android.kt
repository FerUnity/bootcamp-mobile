package com.example.persistenciaynetworkingmultiplatform.sqldelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.persistenciaynetworkingmultiplatform.AppDatabaseExample


actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabaseExample.Companion.Schema, context, "app.db")
    }
}