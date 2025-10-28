package com.example.mi_kmp_demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "mi_kmp_demo",
    ) {
        App()
    }
}