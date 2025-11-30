package com.example.micalendariov2

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "micalendariov2",
    ) {
        App()
    }
}