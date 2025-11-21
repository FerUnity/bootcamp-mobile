package com.example.micalendario

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "micalendario",
    ) {
        App(
            onBackPressed = ::exitApplication
        )
    }
}