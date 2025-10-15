package com.example.proyectopersonal.viewmodel

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.R

//ViewModel para las notificaciones
class NotificationViewModel : ViewModel() {
    //   Var que rep al Canal de notificacion. Obligat desde Android 28:
    private val CHANNEL_ID = "example_channel"


    //    Fun para crear el canal de notificacion:
    fun createNotificationChannel(context: Context) {
//        Si tenemos la vers SDK de Android sobre 28, podemos crear un canal. Antes no se pedia
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Ejemplo"
            val descriptionText = "Descripcion del Canal de Ejemplo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            Finalmente creamos el canal, con la info de arriba
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

//            Creamos el notificationManeger para recibir el canal de notif creado aqui arriba:
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }

    //    Otra fun para que esta app ENVIE una notificacion a la bandeja de notif del dispositivo,
//    que traera el contexto, el titulo y el mensaje:
    @SuppressLint("MissingPermission")
    fun showNotification(context: Context, title: String, message: String) {
//        Creamos un builder, que va a estar escuchando a nuestro canal CHANNEL_ID,
        // para crear la notif a partir de la notif recibida por el notificationManager():
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //Img cuando hay una notificacion. Se puede cambiar ver proy ExamplesScreen
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

//        Y mostramos la notificacion:
        with(NotificationManagerCompat.from(context)){
            notify(System.currentTimeMillis().toInt(), builder.build())

        }



    }
}