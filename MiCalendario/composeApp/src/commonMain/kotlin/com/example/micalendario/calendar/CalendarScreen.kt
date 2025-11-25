package com.example.micalendario.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onDateSelected: (LocalDate) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val today = viewModel.getToday()
    val weeks = buildMonthCells(state.currentMonthStart, state.holidays, today)

    // Estados locales para la selección manual de fecha
    var yearText by remember { mutableStateOf(state.currentMonthStart.year.toString()) }
    var monthText by remember { mutableStateOf(state.currentMonthStart.monthNumber.toString()) }
    var inputError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header Mes + Navegación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { viewModel.goToPreviousMonth() }) {
                Text("<")
            }
            Text(
                text = "${state.currentMonthStart.monthNumber}/${state.currentMonthStart.year}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { viewModel.goToNextMonth() }) {
                Text(">")
            }
        }

        Spacer(Modifier.height(8.dp))

        // seleccionar una fecha y saltar a ese mes
        Text(
            text = "Ir a mes específico",
            style = MaterialTheme.typography.labelMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = yearText,
                onValueChange = {
                    yearText = it
                    inputError = null
                },
                label = { Text("Año (ej. 2025)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = monthText,
                onValueChange = {
                    monthText = it
                    inputError = null
                },
                label = { Text("Mes (1 a 12)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    val year = yearText.toIntOrNull()
                    val month = monthText.toIntOrNull()
                    if (year != null && month != null && month in 1..12) {
                        val date = LocalDate(year, month, 1)
                        viewModel.goToMonthOf(date)
                        inputError = null
                    } else {
                        inputError = "Año o mes inválido"
                    }
                }
            ) {
                Text("Ir")
            }
        }

        inputError?.let { msg ->
            Spacer(Modifier.height(2.dp))
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(Modifier.height(8.dp))

        // Encabezado días de la semana
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("L", "M", "M", "J", "V", "S", "D").forEach { label ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = label, style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        // Semanas
        weeks.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { cell ->
                    DayCellView(
                        cell = cell,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onClick = { date ->
                            date?.let { onDateSelected(it) }
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Mostrar fecha actual
        val todayText = remember(today) {
            "%02d/%02d/%04d".format(today.dayOfMonth, today.monthNumber, today.year)
        }
        Text(
            text = "Fecha de hoy: $todayText",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}