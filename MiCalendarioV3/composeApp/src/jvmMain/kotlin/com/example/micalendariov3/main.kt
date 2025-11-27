package com.example.micalendariov3

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MiCalendarioV3",
    ) {
        App()
    }
}