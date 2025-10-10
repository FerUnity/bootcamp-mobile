package com.example.proyectopersonal.ui.screens.audiorecorder

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.proyectopersonal.model.AudioUIState
import com.example.proyectopersonal.viewmodel.AudioViewModel
import java.util.Locale

//Aca esta la fun composable que rep la vista del audio recorder y el TTS, usando como parm el viewModel,
// porque es el que maneja la logica y las fun a usar.
// La forma en que se llama al viewmodel como param lo crea automaticamente::
@Composable
fun AudioRecorderScreen(viewModel: AudioViewModel = AudioViewModel(), innerPadding: PaddingValues) {
    val context = LocalContext.current //El contexto actual de la app
    val uiState by viewModel.audioUIState.collectAsState()
//El estado actual del microfono que tb lo entrega el viewModel, llamando a la clase AudioUIState

//    var que escribira el texto captado por microfono:
    var recognizedText by remember { mutableStateOf("") }

//    var de una clase especifica, para TTS: tomar texto y leerlo con una voz sintetica:
    //tts es el elemento que va a leer el texto
    var tts: TextToSpeech? by remember { mutableStateOf(null) }
//Y otra var que rep la voz hablada
// NO VBA: y que al abrir la funcion saludara con una frase: "Hola, soy tu asistente de voz.":
    var textToSpeak by remember { mutableStateOf(TextFieldValue("Hola, soy tu asistente de voz.")) }

//    Cargamos el TTS
    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("es", "CL")
            }
        }
    }

//    Detenemos el TTS
    DisposableEffect(Unit) {
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }



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

//    Validamos permisos para reconiocer voz:
    val speechLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
//        Si es que si se pudo rtec la voz
        if (result.resultCode == RESULT_OK) {
//            Guardamos en una lista los datos del texto reconocido, pero siempre tomara el primer: get(0):
           val speechResult: ArrayList<String>? = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recognizedText = speechResult?.get(0) ?: "Capte la voz pero no pude entender el texto hablado"
        }
        else {
            Toast.makeText(context, "No pude captar la voz", Toast.LENGTH_SHORT)
                .show()
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    )
    {
//        Texto que muestra el texto escrito, convertido dede discurso del mic:
        Text(
            text = recognizedText,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        )

//    Hacemos un when para los 4 estados del microfono, segun su AudioUIState:
        when (uiState) {
//        Si esta en reposo llamamos a la val permissionLauncher que gestiona el permiso de grabacion de audio,
//      y que si es aprobado, llama a la fun startRecording del viewModel:
            //      mostrando un btn que diga grabar y llame a la fun correspondiente del viewModel:
            is AudioUIState.Idle -> {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }) {
                    Text("Grabar audio")
                }

//                Y otro boton para reconoicer voz y transcribir a texto:
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.setRecording() //Para que desaprezc los botones q ue no se usaran
//                        Solicitud de recon de voz a traves de un intent:
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL") //Idiona esp chileno
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Ahora habla...")//Voz de bienvenida
                        }
//                        Validamos permisos para rec voz y llamamos al intent,
//                        y arriba en el speechLauincher se realiza el proc de conversion de voz a texto:
                        speechLauncher.launch(intent)
                    }) {
                    Text("Transcribir Voz")
                }
//                Boton para reconocer texto y pronunciarlo:
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        tts?.speak(
//                            textToSpeak.text, //Saludo iunicial. NO VA
                            TextFieldValue(recognizedText).text,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "tts1")
                    },
                    //este btn aparecera solo cuando tenga texto escrito gfuardado (en la var recognizedText)
//                    O sea despued de usar el btn Reconocer Voz y decir algo para que aprezca el texto:
                    enabled = recognizedText.isNotEmpty()
                ) {
                    Text("Leer Texto")
                }
//                OJO FALTARIA UN BOTON:
            //LA IDEA ES QUE MIENTRAS ESTE LEYENDO APAREZCA UN BTN QUER DIGA DETER LECTURA Y QUE LA DETENGA
            }

//        Si esta grabando mostrara un btn que diga detener y llame a la fun correspondiente del viewModel:
            AudioUIState.Recording -> {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = { viewModel.stopRecording() }) {
                    Text("Detener grabación")
                }
            }


            is AudioUIState.Error -> {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "Error: ${(uiState as AudioUIState.Error).message}")
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
                viewModel.setIdle()

            }
        }

    }


}

