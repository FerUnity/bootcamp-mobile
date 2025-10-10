package com.example.proyectopersonal.ui.screens.audiorecorder

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.proyectopersonal.model.AudioUIState
import com.example.proyectopersonal.viewmodel.AudioViewModel

//Aca esta la fun composable que rep la vista del audio recorder, usando como parm el viewModel,
// porque es el que maneja la logica y las fun a usar.
// La forma en que se llama al viewmodel como param lo crea automaticamente::
@Composable
fun AudioRecorderScreen(viewModel: AudioViewModel = AudioViewModel(), innerPadding: PaddingValues) {
    val context = LocalContext.current //El contexto actual de la app
    val uiState by viewModel.audioUIState.collectAsState()
//El estado actual del microfono que tb lo entrega el viewModel, llamando a la clase AudioUIState

//    Validamos los permisos para poder usar el microfono:
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
//            Si se auitoriza la grabacion de audio, llamamos a la fun startRecording del viewModel:
            viewModel.startRecording(context)
        } else {
            Toast.makeText(context, "Permiso de grabación de audio denegado", Toast.LENGTH_SHORT)
                .show()
        }

    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {

//    Hacemos un when para los 4 estados del microfono, segun su AudioUIState:
        when (uiState) {
//        Si esta en reposo llamamos a la val permissionLauncher que gestiona el permiso de grabacion de audio,
//      y que si es aprobado, llama a la fun startRecording del viewModel:
            //      mostrando un btn que diga grabar y llame a la fun correspondiente del viewModel:
            is AudioUIState.Idle -> {
                Button(onClick = {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }) {
                    Text("Grabar audio")
                }
            }

//        Si esta grabando mostrara un btn que diga detener y llame a la fun correspondiente del viewModel:
            AudioUIState.Recording -> {
                Button(onClick = { viewModel.stopRecording() }) {
                    Text("Detener grabación")
                }
            }


            is AudioUIState.Error -> {
                Text("Error: ${(uiState as AudioUIState.Error).message}")
                Row {
//            Boton deter grtabacion si es que no se detuvo correctamente:
                    Button(onClick = { viewModel.stopRecording() }) {
                        Text("Detener grabación")
                    }
//            Un btn para reiniciar la grabación:
                    Button(onClick = { viewModel.startRecording(context) }) {
                        Text("Reintentar grabación")
                    }
                }

            }

            is AudioUIState.Success -> {

                Text("Grabación exitosa: ${(uiState as AudioUIState.Success).filePath}")

            }
        }

    }


}

