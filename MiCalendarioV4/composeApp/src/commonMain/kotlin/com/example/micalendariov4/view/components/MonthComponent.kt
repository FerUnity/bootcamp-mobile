package com.example.micalendariov4.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.micalendariov4.model.MonthObject

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
fun MonthComponent(month: MonthObject) {
    // Se pinta un mes Xs
    Column {
        // Aquí se muestra el nombre del mes y año
        val monthName = month.getLocaleMonth("es", "CL")
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier.width(500.dp)
        ) {
            Text(
                text = monthName + " " + month.year,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )
        }

        // Aquí se muestran los 7 días de la semana en una fila de 7 cuadros verdes:
        Row (
            modifier = Modifier
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
//           USando for se crea un bucle que va a repetir 7 veces, una vez por cada día de la semana,
//            la cracion de cada día se hace con un Card, que a su vez contiene un Box con un Text:
            for (day in 0..6) {
//                El valor de la var day, parte de 0 y se a incrementar de a 1, en cada iteracion:
                Card (
                    modifier = Modifier
                        .width(72.dp)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Green,
                        contentColor = if (day == 6) Color.Red else Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
//                            En cada iteracion va cambiando el nombre del dia en el Text:
                            text = DayOfWeek.values()[day].name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
//        MatrixDeCards()

        // Aquí se muestran los días del mes repartidos en las semanas del mes
        val weeks = month.getWeeksInMonth()
        val startDayOfWeek = month.getStartDayOfWeek()
        val daysInMonth = month.getDaysInMonth()
        var dayNumber = 1
        for (week in 1..weeks) {
            Spacer(modifier = Modifier.width(4.dp))
            Row (
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in 1..7) {
                    if ((week == 1 && day < startDayOfWeek) || dayNumber > daysInMonth) {
                        Spacer(
                            modifier = Modifier
                                .width(72.dp)
                                .padding(4.dp),
                        )
                    } else {
                        Card(
                            modifier = Modifier
                                .width(72.dp)
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(
                                contentColor = if (day == 7) Color.Red else Color.DarkGray
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(dayNumber.toString())
                            }
                        }
                        dayNumber += 1
                    }
                }
            }
        }
    }
}

//Pintamos 1 mes:
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
//            for (i in 0 until 5) {
            CardVertical(cuadroIndex = index)
//            }
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