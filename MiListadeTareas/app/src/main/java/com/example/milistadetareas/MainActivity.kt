package cl.uchile.postgrado.mobile.milistadetareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
//import cl.uchile.postgrado.mobile.milistadetareas.ui.theme.MiListaDeTareasTheme
import com.example.milistadetareas.ui.theme.MiListaDeTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiListaDeTareasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ToDoList(modifier: Modifier = Modifier) {
    // Declaramos las estructura de datos a utilizar en la Screen
    var tarea by remember { mutableStateOf("") }
    val listaTareas = remember { mutableStateListOf<String?>(null) }

    // Definimos la estructura general de la aplicación en formato vertical
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Agregamos el componente que presentará el título
        Text(
            text = "Mi Lista de Tareas",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier
                .padding(8.dp)
        )

        // Agregamos el campo para agregar el nombre de una nueva tarea
        TextField(
            //Asociamos el valor de texto del textField con la var tarea:
            value = tarea,
            onValueChange = { tarea = it },
            label = { Text("Nombre de la tarea") },
            placeholder = { Text("Ingresa una nueva tarea") },
            singleLine = true,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        // Agregamos el botón para agregar una nueva tarea
        Button(
            onClick = {
                //Agregamos el texto tarea al onClick del boton
                listaTareas.add(tarea)
                // Luego borramos el texto de la var tarea,
                // para que se borre autom del textfield y no tener que hacerlo manualmente:
                tarea = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            enabled = tarea.isNotBlank()
        ) {
            // Agregamos el ícono
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar tarea",
                modifier = Modifier.padding(end = 4.dp)
            )

            // Agregamos el texto
            Text("Agregar tarea")
        }

        // Definimos una nueva columna solo para mostrar las tareas
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
                .fillMaxWidth()
        ) {
            // Aca esta la lista que se comienza a llenar,
            // Iteramos por cada una de las tareas almacenadas
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
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Bullet",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Agregamos un separador horizontal
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

@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {
    MiListaDeTareasTheme {
        ToDoList()
    }
}