package com.example.proyectopersonal.ui.screens.notificacionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectopersonal.viewmodel.BiometricViewModel

@Composable
fun BiometricScreen(
    navController: NavController,
    biometricViewModel: BiometricViewModel,
    onAuthenticate: () -> Unit = {}
){
    //    Para auth con biometria, huella digital. Colectamos los estados del uiState obtenidos en el biometricViewModel:
    val state by biometricViewModel.uiState.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding) //Para que quede dentro del scaffold
                .fillMaxSize(),
//            Para que cada elem quede separado del tro en 16 dp
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
//            Si es que se autentifico con biometria se activan todos los campos:
            if (state.authenticated) {
                //Acceder a la pantalla HOME de la app:
                navController.navigate("home")
            /* composable("home") {
                            IndexScreen(navController)
                        }*/

            } //Cierre if
            else {
                //Si no esta auntenticado,
                // que aparezca el boton para autenticar biometricamente con huella digital:
                Button(
                    onClick = {
                        onAuthenticate()

                    },
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Text("Autenticar con Huella Digital")
                }


            }

        } //Cierre Column

    } //Cierre Scafold

}