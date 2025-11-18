package com.example.proyectopersonal.model.notificacionesModel

import android.util.Log
import com.example.proyectopersonal.viewmodel.NotificationViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

//En esta clase referenciamos al Firebase messaging, donde estaran almacenadas varias notificaciones a ser llamadas por la app

class MyFirebaseMessagingService: FirebaseMessagingService() {
    //    Ref al viewModel:
    private val notificationViewModel = NotificationViewModel()

    //    Fun para recibir los nuevos tokens:
    override fun onNewToken(token: String){
        Log.d("MyFirebaseMessagingService", "Refreshed token: $token")

    }

    //    Fun para recibir mensajes remotos: en este caso de Firebase message
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title?: "Notificacion" //Si no tiene titulo, le ponra uno: Notificacion
        val message = remoteMessage.notification?.body?: "" //Si no tiene mensaje en el body, que muestre un mensaje vacio

//    Entonces como hay unlistener escuchando cuando llegan notificaciones de Firebase,
//    entonces cuando lleguen notificaciones externas,
//    llamamos a la fun del vieweModel que las envia a la bandeja de entrada del dispositivo:
        notificationViewModel.showNotification(applicationContext, title, message)

        Log.d("MyFirebaseMessagingService", "Titulo: $title, Mensaje: $message")

    }
}