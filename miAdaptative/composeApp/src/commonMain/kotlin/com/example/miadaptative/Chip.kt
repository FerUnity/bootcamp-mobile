package com.example.miadaptative

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Fun composable para crear cuadritos de texto o Chips:

//Fun para xcrear 10 chips y se acomoden de forma adaptativa:
@Composable
fun Chip(text: String) {
//    Creamos un eapacio (surface) con informacion:
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

    }
}