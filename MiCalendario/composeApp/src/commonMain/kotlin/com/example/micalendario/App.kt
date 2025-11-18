package com.example.micalendario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Date

@Suppress("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
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
    }
}