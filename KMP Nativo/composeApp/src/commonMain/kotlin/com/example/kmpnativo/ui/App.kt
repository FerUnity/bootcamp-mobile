package com.example.kmpnativo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmpnativo.data.UserRepository
import com.example.kmpnativo.model.BatteryLevel
import com.example.kmpnativo.model.getPhoto
import com.example.kmpnativo.model.getSystemUserName
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

//Corresponde a la vista comun en todas las plataformas
@Composable
@Preview
fun App(userRepository: UserRepository) {
    MaterialTheme {
        var scope = rememberCoroutineScope()
        var showContent by remember { mutableStateOf(false) }
//        Como es un formulario hacemos un remember del userName y se obtiene con getSystemUserName():
        var userName by remember { mutableStateOf(getSystemUserName()) }
        var fullName by remember { mutableStateOf("") }
//        La foto puede ser null, de hecho al inicio es null:
        var photo by remember { mutableStateOf<String?>(null) }
        val userExists = userName != ""

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            /* Row {
                 if (userName != null) { //O sea en Android no muestra nada, solo en Desktop
                     Text("User name: $userName")
                 }

                 Button(onClick = { showContent = !showContent }) {
                     Text("Click me!")
                 }
                 if (batteryLevel.getBatteryLevel() != null) {
                     Text("Battery level: $batteryLevel%")
                 }
             }
             AnimatedVisibility(showContent) {
                 val greeting = remember { Greeting().greet() }
                 Column(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalAlignment = Alignment.CenterHorizontally,
                 ) {
                     Image(painterResource(Res.drawable.compose_multiplatform), null)
                     Text("Compose: $greeting")
 //   Llamamos a la fun getUserHomeDir() del Platform,
 //   para obtener la ruta del ext storage para cada plataforma:
                     Text("Home dir: ${getUserHomeDir()}")
                 }
             }
         }*/
            Text("Registro de Usuario", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = userName ?: "",
                onValueChange = { userName = it },
                label = { Text("Nombre de usuario") },
                enabled = !userExists
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                scope.launch {
                    photo = getPhoto()
                }
            }) {
                Text("Seleccionar / Capturar Foto")
            }

            Spacer(Modifier.height(16.dp))

            /* photo?.let {
                val image = SkiaImage.makeFromEncoded(File(it).readBytes())
                Image(bitmap = image.asImageBitmap(), contentDescription = null, modifier = Modifier.size(120.dp))
            } */

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                userRepository.insertUser(userName ?: "", fullName, photo)
            }) {
                Text("Guardar Usuario")
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}