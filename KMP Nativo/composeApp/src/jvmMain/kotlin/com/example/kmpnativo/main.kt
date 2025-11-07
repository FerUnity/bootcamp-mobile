package com.example.kmpnativo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kmpnativo.model.DesktopBatteryLevel
import com.example.kmpnativo.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmpnativo",
    ) {
        //Aca calculamos el BATT level cuya clase DesktopBatteryLevel esta el platforn de jvm,
        // pero ojo siempre sera null en desktop:
        val batteryLevel = DesktopBatteryLevel()
        App(batteryLevel) //En Desktop mostrara siempre null
    }
}