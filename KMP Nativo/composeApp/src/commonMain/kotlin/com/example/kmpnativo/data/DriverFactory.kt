package com.example.kmpnativo.data

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
//  Crea el Driver automaticamente para Android y Desktop en este caso:
    fun createDriver(): SqlDriver

}