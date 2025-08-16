package com.example.proyectopersonal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,//es una propiedad que define la lista de fuentes a usar para el texto.
        fontWeight = FontWeight.Normal, //propiedad que define el grosor o negrita de la fuente de un texto.
        fontSize = 16.sp, //propiedad que define el tamaño de fuente de un texto.
        lineHeight = 24.sp,//propiedad que define la altura de línea de un texto. Controla el espacio vertical entre líneas
        letterSpacing = 0.5.sp //propiedad que define el espaciado entre letras de un elemento de texto.
    ),
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp
    )

)