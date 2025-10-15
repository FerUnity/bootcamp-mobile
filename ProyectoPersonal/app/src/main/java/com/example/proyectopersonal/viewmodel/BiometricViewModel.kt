package com.example.proyectopersonal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.notificacionesModel.BiometricUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//Clase que controla el estado de la autenticacion biometrica:
class BiometricViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BiometricUIState())
    val uiState: StateFlow<BiometricUIState> = _uiState.asStateFlow()

    fun setAuthenticated(authenticated: Boolean) {
        _uiState.value = if(authenticated){
            BiometricUIState(authenticated = true, message = "Autenticacion exitosa")
        } else {
            BiometricUIState(authenticated = false, message = "Autenticacion fallida")
        }
    }



}