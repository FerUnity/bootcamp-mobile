package com.example.proyectopersonal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Esta fun se llama en el TopBar.kt, genera un cuadro de dialogo al iniciar la app que pregunta en que modo trabajar:
//Claro, oscuro o System
@Composable
fun ThemeSettingDialog(
    showDialog: Boolean,
    title: String,
    onDismiss: () -> Unit,
    currentTheme: String,
    onThemeChange: (String) -> Unit) {

    var selectedTheme by remember { mutableStateOf(currentTheme) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTheme = "light"
                                onThemeChange("light")
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = selectedTheme == "light",
                            onClick = {
                                selectedTheme = "light"
                                onThemeChange("light")
                            }
                        )
                        Text("Modo Claro")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTheme = "dark"
                                onThemeChange("dark")
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = selectedTheme == "dark",
                            onClick = {
                                selectedTheme = "dark"
                                onThemeChange("dark")
                            }
                        )
                        Text("Modo Oscuro")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTheme = "system"
                                onThemeChange("system")
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = selectedTheme == "system",
                            onClick = {
                                selectedTheme = "system"
                                onThemeChange("system")
                            }
                        )
                        Text("Predeterminado del Sistema")
                    }
                }
            },
            confirmButton = {
                Text(text = "Cerrar",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onDismiss() })
            }
        )
    }
}