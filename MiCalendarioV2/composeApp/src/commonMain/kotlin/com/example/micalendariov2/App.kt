package com.example.micalendariov2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import micalendariov2.composeapp.generated.resources.Res
import micalendariov2.composeapp.generated.resources.compose_multiplatform

// Define tu datos de ejemplo
val dataListHorizontal = List(7) { it }
val dataListVertical = List(4) { it }
@Composable
fun App() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Este es el contenedor principal que se desplaza verticalmente
        LazyVerticalGrid(
            // Define los límites de la rejilla
            columns = GridCells.Fixed(4),
            // Agrega el padding necesario
            contentPadding = PaddingValues(8.dp)
        ) {
            // Itera sobre los datos para crear 4 filas de cards verticales
            items(dataListVertical) { rowIndex ->
                // Cada card vertical contiene una fila de cards horizontales
                LazyRow(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    // Itera sobre los datos para crear los 7 cards horizontales
                    items(dataListHorizontal) { columnIndex ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "Card ($rowIndex, $columnIndex)",
                                modifier = Modifier.padding(16.dp)
                            )

                        }

                        }

                }
            }
        }
    }

}
