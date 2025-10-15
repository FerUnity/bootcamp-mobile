package com.example.proyectopersonal.model.notificacionesModel

data class BiometricUIState(
    val authenticated: Boolean = false,
    val message: String = "Esperando autenticacion...",
)
