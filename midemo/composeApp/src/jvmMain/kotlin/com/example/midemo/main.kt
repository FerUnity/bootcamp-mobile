package com.example.midemo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.midemo.ui.App
import com.example.midemo.ui.AppDesktop

fun main() = application {
//    La fun Window es una fun que permite manipular la pantalla:
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hola Mundo",
    ) {
//        App()
//  Llamamos a la fun AppDesktop():
        AppDesktop()
    }

}