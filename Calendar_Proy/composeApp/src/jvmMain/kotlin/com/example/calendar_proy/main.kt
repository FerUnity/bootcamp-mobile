package com.example.calendar_proy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "calendar_proy",
    ) {
        App()
    }
}