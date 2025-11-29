package com.example.micalendariov3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Define tu datos de ejemplo
/*val dataListHorizontal = List(7) { it }
val dataListVertical = List(4) { it }*/
@Composable
fun App() {
    /* MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }*/
    // Box para envolver toda la estructura
    /* Box(modifier = Modifier.fillMaxSize()) {
        // LazyVerticalGrid para las 4 filas verticales
        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // Define 4 columnas, por lo que habrá 4 filas
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Espacio vertical entre los ítems de la cuadrícula
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio horizontal entre los ítems de la cuadrícula
        ) {
            items(4) { rowIndex -> // 4 filas verticales
                // LazyRow para los 7 elementos horizontales dentro de cada fila
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacio horizontal entre los ítems de la fila
                    modifier = Modifier.height(100.dp) // Altura de cada fila
                ) {
                    items(7) { columnIndex -> // 7 elementos horizontales
                        // Cada elemento horizontal
                        Card(
                            modifier = Modifier
                                .width(150.dp) // Ancho de cada card horizontal
                                .height(100.dp) // Altura de cada card horizontal
                                .background(Color.Gray) // Color de fondo del card
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Fila ${rowIndex + 1}, Col ${columnIndex + 1}")
                            }
                        }
                    }
                }
            }
        }
    }*/

    MatrixDeCards()
}

enum class DayOfWeek {
    Lu,
    Ma,
    Mi,
    Ju,
    Vi,
    Sa,
    Do
}

@Composable
fun MatrixDeCards() {
    // Un Box podría ser usado para envolver toda la matriz si se necesita.
    Box(modifier = Modifier.fillMaxSize()) {
        // LazyRow para la fila horizontal de 7 cuadros
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(7) { index ->
                // Llama a la función para crear cada cuadro horizontal
//                CuadroHorizontal(index = index)
                CuadroHorizontal(index = DayOfWeek.values()[index].name)
            }
        }
    }
}

@Composable
fun CuadroHorizontal(index: String) {
    // Aquí es donde cada cuadro horizontal se define.
    // Podría ser un Card o un Box. QUE ENGLOBA EL MES COMPLETO
    Card(
        modifier = Modifier
            .width(200.dp) // Ancho del cuadro horizontal
            .height(350.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
//            PARA CREAR LAS CARDS VERTICALES:
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Llama a la función para crear las cards verticales
            for (i in 0 until 5) {
                    CardVertical(cuadroIndex = index)
//                CardVertical(DayOfWeek.values()[i].name)
            }
        }
    }
}

@Composable
//    fun CardVertical(cuadroIndex: String, cardIndex: Int) {
fun CardVertical(cuadroIndex: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), // Altura de cada card vertical
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Text(
//                    text = "Cuadro ${cuadroIndex + 1}, Card ${cardIndex + 1}"
                text = cuadroIndex
            )
        }
    }
}