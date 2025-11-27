package com.example.micalendario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import micalendario.composeapp.generated.resources.Res
import micalendario.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
//Esta version es con DatePicker:


fun App() {
    MaterialTheme {
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(5) // 5 columnas verticales
        ) {
            items(7) { rowIndex -> // 7 "filas" de tarjetas
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1) // Solo una fila por cada tarjeta
                ) {
                    items(7) { columnIndex -> // 7 tarjetas horizontales
                        // Aquí va tu diseño de tarjeta (por ejemplo, un composable Card)
                        Card(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "Fila $rowIndex, Columna $columnIndex",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}