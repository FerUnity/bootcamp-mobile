package com.example.kmpnativo.model

import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog

actual fun getUserName(): String {
    return System.getProperty("user.name")?: ""
}

//Con la sgte fun buscamos un archivo de foto en el dispositivo desktop:
actual suspend fun getPhoto(): String? {
    val fd = FileDialog(ComposeWindow(), "Seleccionar foto", FileDialog.LOAD)
    fd.isVisible = true
    val file = fd.files.firstOrNull()//Que muestre el primero que encuentre, sino null
    return file?.absolutePath //Retorne la ruta del archivo

}