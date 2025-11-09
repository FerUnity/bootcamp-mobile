package com.example.kmpnativo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kmpnativo.data.DriverFactory
import com.example.kmpnativo.data.UserRepository
import com.example.kmpnativo.model.DesktopBatteryLevel
import com.example.kmpnativo.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Registro de Usuario",
    ) {
        //Aca calculamos el BATT level cuya clase DesktopBatteryLevel esta el platforn de jvm,
        // pero ojo siempre sera null en desktop:
        val repo = UserRepository(DriverFactory())
        App(repo) //
    }
}