package com.example.micalendario.sqldelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.micalendario.AppDatabaseExample

actual class DatabaseDriverFactory(private val context: Context)  {
    actual fun createDriver(): SqlDriver {
       return AndroidSqliteDriver(AppDatabaseExample.Companion.Schema, context,"app.db")
    }
}