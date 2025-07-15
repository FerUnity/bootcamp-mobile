package com.example.proyectopersonal

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.proyectopersonal.ui.theme.ProyectoPersonalTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.ui.text.style.TextDecoration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoPersonalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MiProyecto(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MiProyecto(name: String, modifier: Modifier = Modifier) {
    // Declaramos las 2 estructuras de datos a utilizar en la Screen
    var tarea by remember { mutableStateOf("") }
    val listaTareas = remember { mutableStateListOf<String?>(null) }
    //Vars para el menu desplaegable de Hospitales:
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Opción 1", "Opción 2", "Opción 3")
    var selectedIndex by remember { mutableStateOf(0) }

    // Definimos la estructura general de la aplicación en formato vertical
    Column(
        //Centramos los elem cvontenidos horizontalmente:
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = Color.LightGray)

    ) {
        // Agregamos el componente que presentará el título
        Text(
            text = "Sistema de Orientación Hospitalaria",
            style = MaterialTheme.typography.headlineLarge,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 12.dp)
                .fillMaxWidth()
        )

        //Agregamos un menu desplegable de Hpsitales:

            Text(
                text = "Seleccione Hospital",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                //.fillMaxWidth()
            )

            Text(
                text = items[selectedIndex],
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }, text = "") {
                        Text(s)
                    }

                }
            }
        // Agregamos un separador horizontal entre cada texto ingresado:
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
        )

        //Agregamos un menu desplegable de Especialidades:

        Text(
            text = "Seleccione Especialidad",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Magenta,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
            //.fillMaxWidth()
        )

        Text(
            text = items[selectedIndex],
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }, text = "") {
                    Text(s)
                }

            }
        }

        // Agregamos un separador horizontal entre cada texto ingresado:
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
        )



        // Agregamos el campo para agregar el nombre de una nueva tarea
        Text(
            text = "Ingrese lista de medicamentos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
            //.fillMaxWidth()
        )
        TextField(
            //Asociamos el valor de texto del textField con la var string tarea,
            // def arriba:
            value = tarea,
            onValueChange = { tarea = it },
            label = { Text("Medicamento") },
            placeholder = { Text("Ingresa Medicamento") },
            singleLine = true,
            modifier = Modifier
                //Hacia todas partes esta el padding de 8dp
                .padding(8.dp)
                .fillMaxWidth()
        )

        // Agregamos el botón para agregar una nueva tarea
        Button(
            onClick = {
                //Con onClick, agregamos el texto tarea a la listaTareas:
                listaTareas.add(tarea)
                // Luego borramos el texto de la var tarea,
                // para que se borre autom del Textfield y no tener que hacerlo manualmente:
                tarea = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                //Cuando escriba en el textField se vera
                // el fondo del btn rojo y su texto blanco
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            //Aqui se habilita el btn cuando se ewcriba algo en el textField:
            enabled = tarea.isNotBlank()
        ) {
            // Agregamos el ícono de signo + y el texto del btn
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar tarea",
                //Espacio de 4 dp entre el icono "+" y el texto "Agregar tarea"
                modifier = Modifier.padding(end = 4.dp)
            )

            // Agregamos el texto.
            // Ojo el txt en los botones, siempre viene centrado (Por Material design)
            Text("Agregar tarea")
        }

        // Definimos una nueva columna solo para mostrar la listaTareas,
        //esta column hereda de la column de arriba, que la contiene
        Column(
            modifier = Modifier
                //El weight(1f) en este caso nos indica la cantidad de espacio vertical,
                // vertical porque es una Columna, que usara esta columna hacia abajo
                //  y por ende el widget listaTareas, del resto del espacio disponible,
                //O sea debajo del boton,
                //tb de la listaTareas porque esta columna la contiene:
                //En este caso es 1f(fraccion), o 1:1, o sea que esta col y por ende la listaTareas,
                // ocupara el total del espacio disponible hacia abajo.
                //Com esto evito dar un tamaño numerico que no va a ser igual en todos los dispositivos,
                // por ende se puede cortar o quedar pasado la pantalla
                .weight(1f)
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
                .fillMaxWidth()
        ) {
            // Aca esta el pintado de la lista que se comienza a llenar,
            // Iteramos por cada una de las tareas almacenadas.

            //Aca con el foreach recorremos cada tarea(var string ingresada en el TextField),
            // de la listaTareas,
            // Si la var tarea existe, KLin llama it a cada elem del foreach
            // los cuales seran presentados
            // y cerraran con la linea horizontal por abajo
            listaTareas.forEach { tarea ->
                tarea?.let {
                    // Cada tarea es presentada como un ícono y texto en una fila
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        //Icono de visto bueno o check:
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Bullet",
                            //Separacion entre el check y el texto ingresado
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        //Y el texto que se traspaso desde el TextField: text = it,
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Agregamos un separador horizontal entre cada texto ingresado:
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                    )
                }
            }
        }
    }
}

private fun ColumnScope.DropdownMenuItem(
    onClick: () -> Unit,
    text: String,
    interactionSource: @Composable () -> Unit
) {
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPersonalTheme {
        MiProyecto("Android")
    }
}