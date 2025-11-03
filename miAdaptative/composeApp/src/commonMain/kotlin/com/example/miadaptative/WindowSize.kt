package com.example.miadaptative

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.FontWidth

//USamos este archivo de commonMain, para definir el nombre del ancho del enum,
// Y luego en una fun composable,
// asignamos esos nombres segun los dp utilizados en el parametro maxWidth
enum class WindowSize { COMPACT, MEDIUM, EXPANDED}

//La fun en cuestion se referencia desde AdaptativeScreen.kt
@Composable
fun getWindowSizeClass(maxWidth: Dp): WindowSize = when {
    maxWidth < 600.dp -> WindowSize.COMPACT //Si ancho de espacio dispon en pantalla es < 600 dp
    maxWidth < 840.dp -> WindowSize.MEDIUM // Si ancho de espacio dispon en pantalla es >= 600 dp y < 840 dp
    else -> WindowSize.EXPANDED // Si ancho de espacio dispon en pantalla es >= 840


}