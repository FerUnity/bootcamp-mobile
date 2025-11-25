package com.example.micalendario

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.micalendario.calendar.CalendarViewModel
import com.example.micalendario.calendar.DayCellView
import com.example.micalendario.calendar.buildMonthCells
import kotlinx.datetime.LocalDate
import kotlin.collections.forEach

@Composable
fun DesktopCalendarRoot(
    viewModel: CalendarViewModel,
    windowWidth: Dp
) {
    val state by viewModel.state.collectAsState()
    val today = viewModel.getToday()

    // Elegimos cuántos meses mostrar según ancho de ventana
    val monthsToShow = when {
        windowWidth < 800.dp -> 1
        windowWidth < 1200.dp -> 2
        else -> 3
    }

    // Meses que se mostrarán: mes base + 1, +2 ...
    val monthStarts: List<LocalDate> = remember(state.currentMonthStart, monthsToShow) {
        (0 until monthsToShow).map { offset ->
            addMonths(state.currentMonthStart, offset)
        }
    }

    // Asegurarnos de cargar feriados para TODOS los años visibles
    val yearsVisible = monthStarts.map { it.year }.toSet()
    LaunchedEffect(yearsVisible) {
        yearsVisible.forEach { year ->

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header común: flechas y título (mes base)
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
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = { viewModel.goToNextMonth() }) {
                Text(">")
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 Mostramos 1, 2 o 3 meses en fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            monthStarts.forEach { monthStart ->
                val weeks = buildMonthCells(monthStart, state.holidays, today)

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Título del mes individual
                    Text(
                        text = "${monthStart.monthNumber}/${monthStart.year}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(4.dp))

                    // Encabezado días
                    Row(modifier = Modifier.fillMaxWidth()) {
                        listOf("L", "M", "M", "J", "V", "S", "D").forEach { label ->
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium
                                )
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
                                    onClick = { /* en desktop no necesitamos selección por ahora */ }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun addMonths(date: LocalDate, delta: Int): LocalDate {
    val newMonthNumber = date.monthNumber + delta
    val yearShift = floorDiv(newMonthNumber - 1, 12)
    val normalizedMonth = ((newMonthNumber - 1) % 12 + 12) % 12 + 1
    val year = date.year + yearShift
    return LocalDate(year, normalizedMonth, 1)
}

fun floorDiv(a: Int, b: Int): Int {
    val q = a / b
    val r = a % b
    return if (r != 0 && ((a xor b) < 0)) q - 1 else q
}
