package com.example.proyectopersonal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun ProyectoPersonalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        //Esto es valido solo para Dynamic Color Scheme:
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        //Si no tengo  Dynamic Color Scheme, solo decido entre ambos modos del telefono:

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        //En el colorScheme se llama a la fun colorScheme(),
        // en que se define si el cel esta en modo oscuro(DarkColorScheme) o claro(LightColorScheme)
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShape,
        content = content
    )
}