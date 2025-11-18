package com.example.proyectopersonal.model

import android.net.Uri

//Se presentan los 4 estados que tendra tanto la camara como la geleria
//Idle: durmiendo,
// Loading: cargando la camara o galeria,
// Success: exitoso, mostrara la img guardada en su uri,
// Error: mostarar mensaje de error:


sealed class MediaUIState {
    object Idle : MediaUIState()
    object Loading : MediaUIState()
    data class Success(val uri: Uri) : MediaUIState()
    data class Error(val message: String) : MediaUIState()
}
