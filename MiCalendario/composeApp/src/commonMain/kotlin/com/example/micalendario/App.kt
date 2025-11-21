package com.example.micalendario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.micalendario.calendar.Calendar
import com.example.micalendario.calendar.CalendarViewModel
import com.example.micalendario.calendar.CalendarYear
import com.example.micalendario.calendar.model.CalendarDay
import com.example.micalendario.calendar.model.CalendarMonth
import com.example.micalendario.calendar.model.DaySelected
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
//Esta version es con DatePicker:
fun App(
    onBackPressed: () -> Unit
) {
    /*    MaterialTheme {
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

/*
//Version con DatePicker

Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
//        var showDate by remember { mutableStateOf(false) } //Se muestra o no el calendar
        var showDialog by remember { mutableStateOf(false) } //Se muestra o no el dialog
        val state = rememberDatePickerState() //Estado calendar
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Button(onClick = {
                showDialog = true
            }) {
                Text("Mostrar Fecha")
            }

            if (showDialog) {
//                Al presionar el boton Mostrar fecha se muestra el dialog con el calendar
                //y los botones para seleccionar fecha o cancelar.
                DatePickerDialog(
                    onDismissRequest = {
//                    Boton X del Dialog para cerrar el calendar
                        showDialog = false
                    },
                    confirmButton = {
//                        LUEGO DE SELECC LA FECHA, CIERRA EL CALENDARIO
                        Button(
                            onClick = {
                                showDialog = false
                            }
                        )
                        {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
//                        Aparece un nuevo boton llamado Cancelar que solo cierra el dialog
                        OutlinedButton(
                            onClick = {
                                showDialog = false
                            }
                        )
                        {
                            Text("Cancelar")
                        }
                    }
                )
                {
                    // Fun para mostrar calendario, en donde seleccionaremos una fecha
                    // que quedara guardada mas abajo en la var date, por el uso de la var state:
                    DatePicker(state = state)
                }

            }
            //Muestra la fecha seleccionada en milisegundos:
            val date = state.selectedDateMillis
//            Si date no es null que se muestre la fecha seleccionada:
            date?.let {
//                val instant = ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate() dfsdzgfdfgsdgdsgdsds
                val fecha = Date(date)
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val formattedDate = formatter.format(fecha)
                Text("Fecha Seleccionada: $formattedDate")
            }
        }
    }*/

    val calendarViewModel: CalendarViewModel = viewModel()
    val calendarYear = calendarViewModel.calendarYear

    CalendarContent(
        selectedDates = calendarViewModel.datesSelected.toString(),
        calendarYear = calendarYear,
        onDayClicked = { calendarDay, calendarMonth ->
            calendarViewModel.onDaySelected(
                DaySelected(calendarDay.value.toInt(), calendarMonth, calendarYear)
            )
        },
        onBackPressed = onBackPressed
    )
}

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarContent(
    selectedDates: String,
    calendarYear: CalendarYear,
    onDayClicked: (CalendarDay, CalendarMonth) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.White),
        topBar = {
           CalendarTopAppBar(selectedDates, onBackPressed)
        }
    ) {
        Calendar(calendarYear, onDayClicked)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopAppBar(selectedDates: String, onBackPressed: () -> Unit) {
    Column {
        Spacer(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(Color.Blue)
        )
        TopAppBar(
            title = {
                Text(
                    text = selectedDates.ifEmpty { "Select Dates" },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            Modifier.background(Color.Red)
//            elevation(0.dp)

        )
    }


}