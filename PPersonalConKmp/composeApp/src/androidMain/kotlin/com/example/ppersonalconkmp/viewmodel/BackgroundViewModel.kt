package com.example.proyectopersonal.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.notificacionesModel.TrackingService

class BackgroundViewModel : ViewModel() {
    //    fun para iniciar seguimiento de recopilar info del user en cada momento en segundo plano:
    fun startTracking(context: Context) {
//        Un intent es una intencion de realizar una accion, en este caso para iniciar el servicio de seguimiento.
        //Un intent se usa para iniciar un servicio, otras actividades o componentes:
        val intent = Intent(context, TrackingService::class.java)
        context.startForegroundService(intent)
    }

    //    fun para detener el seguimeinto de recopilacion de info del usuario:
    fun stopTracking(context: Context) {
        val intent = Intent(context, TrackingService::class.java)
        context.stopService(intent)
    }
}