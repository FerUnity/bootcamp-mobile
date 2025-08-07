package com.example.proyectopersonal.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val BackPurple80 = Color.Black

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF5D546F)
val Pink40 = Color(0xFF7D5260)

val BackPurple40 = Color.White

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    secondary = PurpleGrey80,
    onSecondary = Color.White,
    tertiary = Pink80,
    background = BackPurple80
)

val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.Black,
    secondary = PurpleGrey40,
    onSecondary = Color.Black,
    tertiary = Pink40,
    background = BackPurple40,
    surface = Color(0xFFFFFBFE)
)
