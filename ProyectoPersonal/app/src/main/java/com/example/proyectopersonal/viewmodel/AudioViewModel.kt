package com.example.proyectopersonal.viewmodel

import android.content.Context
import android.media.MediaRecorder
import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.AudioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//Clase viewModel para gestionar el uso del microfono y la grabacion:

class AudioViewModel : ViewModel() {
    //    Generamos la val que muestra el estado actual del microfono: AudioUIState,
    //   primero como var mutable: _audioUIState
    private val _audioUIState = MutableStateFlow<AudioUIState>(AudioUIState.Idle)
//    Se supóne que parte como Idle, por eso se pone ese estado por defecto

    //    Y luego como var de solo lectura(no mutable): audioUIState, que actua como un getter,
//    que sera un observador de los cambios de la val _audioUIState(AudioUIState) almacenando lo que _audioUIState reciba:
    val audioUIState: StateFlow<AudioUIState> = _audioUIState.asStateFlow()

    //    Para la grabacion del audio en el almacenamiento interno o externo,
//    usaremos la API de grabacion de Android: MediaRecorder, importando su biblioteca:
    private var recorder: MediaRecorder? = null

    //    Y una var que sera el nombre del archivo de audio:
    private var outputFile: String =
        "" //No como null para que siempre tenga que tener un valor string

    //    fun que manejaran al microfono:
    fun startRecording(context: Context) {
//        El archivo de salida tendra formato 3gp.
        // Y su nombre sera fecha y hora actual en milisegundos: System.currentTimeMillis()
        outputFile = "${context.filesDir.absolutePath}/${System.currentTimeMillis()}.3gp"
//    Luego creamos un objeto (recorder) para poder usar el mic y grabar, definiendo:
//    Micorofono, formato de salida, codificador de audio(AMR_NB) y archivo(nombre y ruta) de salida:
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)
//            Luego preparamos el mic e iniciamos la captura en try catch:
            try {
                prepare()
                start()
                _audioUIState.value =
                    AudioUIState.Recording //Cambiamos el estado del mic a grabando del tipo AudioUIState.
            } catch (e: Exception) {
                _audioUIState.value =
                    AudioUIState.Error(e.message ?: "Error al iniciar la grabación")
//                Si hay error cambia el estado del mic a error del tipo AudioUIState, entregando el mensaje de error.

            }
        }
    }

    //    Creamos otra fun para detener la grabacion y liberar recursos:
    fun stopRecording() {
        try {
            recorder?.apply {
                stop() //Detener captura
                release() //Liberar recurso del mic
            }
            recorder = null //Destruyo el objeto recorder porque ya no lo necesito.

//    Y cambio el estado del mic a grabacion exitosa del tipo AudioUIState:
            if (outputFile.isNotEmpty()) {
                _audioUIState.value = AudioUIState.Success(outputFile)
            }
        } catch (e: Exception) {
            _audioUIState.value = AudioUIState.Error(e.message ?: "Error al detener la grabación")
        }

    }

//    Editamos la fun de sistema que es automatica para limpiar la pantalla,
//    pero ademas le agregamos eliminar objeto recorder:
    override fun onCleared() {
       super.onCleared()
        recorder?.release() //Liberar recurso del mic
        recorder = null
    }

}


