package com.example.proyectopersonal.model


//USamos esta clase como sealed class, para guardar los 4 estados del microfono:
//Nada, grabando(Capturando mejor), grabacion exitosa o error
sealed class AudioUIState {
    object Idle : AudioUIState()
    object Recording : AudioUIState()
    data class Success(val filePath: String) : AudioUIState()
    //Si la grab fue exitosa devuele la ruta del almac interno, donde quedo el archivo de audio.

    data class Error(val message: String) : AudioUIState()//Devuelve mensaje de error
}