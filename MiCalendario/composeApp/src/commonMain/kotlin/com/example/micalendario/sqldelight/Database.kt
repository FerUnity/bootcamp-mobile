package com.example.micalendario.sqldelight

import com.example.micalendario.AppDatabaseExample

class Database(factory: DatabaseDriverFactory) {
    private val driver = factory.createDriver()
    private val database = AppDatabaseExample.Companion(driver)
    val userQueries = database.userQueries



}