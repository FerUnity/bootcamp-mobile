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
//    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    //Para usar DATASTORE se debe ajustar asi el theme, porque se def como String en userSettingViewModel:
    theme: String,
    content: @Composable () -> Unit
) {
    val colorScheme = when(theme) {
        "Light" -> LightColorScheme
        "Dark" -> DarkColorScheme
        else ->
            //Si viene otra cosa, que solo puede ser la conf del sistema,
            // hay que preguntar si el sistema esta en modo oscuro y llamamos a ese modo, sino en claro:
            if (isSystemInDarkTheme())
                DarkColorScheme
            else
                LightColorScheme
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