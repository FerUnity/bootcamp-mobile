package com.example.midemo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

// Esta fun es la vista en Desktop, por ende se llamara desde el main de JVM:
//Esta fun sera casi igual a la fun App() de commonMain, solo que tendra un texto arriba.
// De hecho tambien llama a la fun App() del commonMain.

@Composable
fun AppDesktop() {
    MaterialTheme {
        Scaffold(
            containerColor = Color.Blue,
            contentColor = Color.White,
            topBar = {
                androidx.compose.material3.Text(
                    fontWeight = FontWeight.Bold,
                    text = "Hola Mundo !!"
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)

                ) {
                    Text(
                        color = Color.White,
                        text = "Estoy en Desktop"
                    )
//        Llamamos a la fun App() del ui del commonMain:
                    App()
                }

            }
        )
    }
}