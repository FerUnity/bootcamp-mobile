package com.example.kmpnativo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kmpnativo.model.BatteryLevel
import com.example.kmpnativo.model.Greeting
import com.example.kmpnativo.model.getSystemUserName
import com.example.kmpnativo.model.getUserHomeDir
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmpnativo.composeapp.generated.resources.Res
import kmpnativo.composeapp.generated.resources.compose_multiplatform
//Corresponde a la vista comun en todas las plataformas
@Composable
@Preview
fun App(batteryLevel: BatteryLevel) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var userName: String? = null

        try {
            userName = getSystemUserName()
        } catch (e: UnsupportedOperationException)
        {
//            Nada
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
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
        }
    }
}