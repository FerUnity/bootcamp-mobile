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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.micalendariov4.database.Holiday
import com.example.micalendariov4.model.MonthObject
import com.example.micalendariov4.viewmodel.CalendarViewModel

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
fun MonthComponent(month: MonthObject, viewModel: CalendarViewModel) {
//    Buscamos los dias feriados colectados desde el viewnodel:
    val holidays by viewModel.holydays.collectAsState()
    viewModel.getHolidaysFromMonth(month.month, month.year.toLong())

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

        // Aquí se muestran los días de la semana
        Row (
            modifier = Modifier
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            for (day in 0..6) {
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
                            text = DayOfWeek.values()[day].name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Aquí se muestran los días del mes repartidos en las semanas del mes
        val weeks = month.getWeeksInMonth()
        val startDayOfWeek = month.getStartDayOfWeek()
        val daysInMonth = month.getDaysInMonth()
        var dayNumber = 1L
        for (week in 1..weeks) {
            Spacer(modifier = Modifier.width(4.dp))
            Row (
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in 1..7) {
//                    SI el num del dia esta fuera del mes que corresponde creamos un espacio:
                    if ((week == 1 && day < startDayOfWeek) || dayNumber > daysInMonth) {
                        Spacer(
                            modifier = Modifier
                                .width(72.dp)
                                .padding(4.dp),
                        )
                    }
//                    SI el rango de dias esta dentro del mes correspond pintamos los dias:
                    else {
                        Card(
                            modifier = Modifier
                                .width(72.dp)
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(
//                   Pintamos los dias de la semana:
//                   Se pinta de rojo los domingos y los feriados que se colectaron en el viewmodel desde la API,
//                   si no son domingos o feriados, se pintan de color gris oscuro:
                                contentColor = if (day == 7 || isHolyday(dayNumber, holidays)) Color.Red else Color.DarkGray
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

fun isHolyday(day: Long, holidays: List<Holiday>) : Boolean {
    if (holidays.isEmpty()) {
        return false;
    }
    for (h in holidays) {
        if (h.day == day) {
            return true;
        }
    }
    return false;
}