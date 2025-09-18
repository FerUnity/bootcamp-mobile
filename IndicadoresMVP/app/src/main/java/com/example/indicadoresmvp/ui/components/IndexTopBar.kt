package com.example.indicadoresmvp.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class Options(
    val title: String,
    val icon: ImageVector
) {
    PROFILE("Perfil de Usuario", Icons.Default.Person),
    SETTINGS("Configuraciones", Icons.Default.Settings)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexTopBar(){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                "Indicadores Economicos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
//            Un boton que abre y cierra el toolBar:
            IconButton(
                onClick = { expanded = true }
            ) {
//                Y este btn tendra el Icono de 3 puntos o MoreVert:
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Toolbar"
                )
            }
//            Al presionar los 3 puntos se abrira un dexplegable con 2 opciones:
//            Perfil de usuario y Configuraciones
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
//                Aca recorremos con la var option, los elementos de: enum class Options(), de arriba, con las 2 opciones del desplegable:
                Options.entries.forEach { option ->
                    DropdownMenuItem(
//                        Texto: Perfil de usuario:
                        text = { Text(option.title) },
                        onClick = { /* Handle profile click */ },
                        leadingIcon = {
                            Icon(
//                                Icono de usuario de cuenta
                                imageVector = option.icon,
                                contentDescription = option.title
                            )
                        }
                    )
                }
            }
        }
    )
//    Text(
//        "Indicadores Economicos",
//        style = MaterialTheme.typography.headlineLarge,
//        modifier = Modifier.padding(16.dp)
//
//    )
}