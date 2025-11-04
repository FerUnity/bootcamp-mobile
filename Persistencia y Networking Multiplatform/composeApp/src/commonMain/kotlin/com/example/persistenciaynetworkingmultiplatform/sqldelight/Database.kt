package com.example.persistenciaynetworkingmultiplatform.sqldelight

import com.example.persistenciaynetworkingmultiplatform.AppDatabaseExample

class Database(factory: DatabaseDriverFactory) {
    private val driver = factory.createDriver()
    private val database = AppDatabaseExample.Companion(driver)

    val userQueries = database.userQueries
}