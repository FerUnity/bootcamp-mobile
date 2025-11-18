package com.example.proyectopersonal.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.MediaUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Este viewModel permite controlar la captura con camara y guardado de la img en el dispositivo,
// para luego abrirla o bien mostar error.
class MediaViewModel: ViewModel() {
    private val _mediaUIState = MutableStateFlow<MediaUIState>(MediaUIState.Idle)
    val mediaUIState: StateFlow<MediaUIState> = _mediaUIState

    //    Si la img se captura correctamente con la camara o bien se obtiene de la Gallery,
//    La img ret un uri de donde se guardo, para luego abrirla con MediaUIState.Success(uri)
//    de la sealed class MediaUIState:
    fun onMediaCaptured(uri: Uri) {
        Log.d("MediaViewModel", "onMediaCaptured: $uri")
        //Log.d es para verificar la captura en el logcat, o sea que imprima errores o success
        _mediaUIState.value = MediaUIState.Success(uri)
    }

    //    Si se produce un error en la captura de Camera o Gallery, se mostrara un mensaje de error con MediaUIState.Error(message),
//    de la sealed class MediaUIState:
    fun onError(message: String) {
        _mediaUIState.value = MediaUIState.Error(message)
    }
}
