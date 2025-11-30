package com.example.micalendariov4

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.micalendariov4.view.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MiCalendarioV4",
    ) {
        App()
    }
}