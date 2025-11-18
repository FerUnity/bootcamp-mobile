package com.example.proyectopersonal.model.notificacionesModel

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

//Servicios en segundo plano
class TrackingService: Service() {
    //    Generamos un canal para las notificaciones:
    private val CHANNEL_ID = "tracking_channel"

    //    Debe implem esta fun que devuelve un binder:
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()

    }

    //    Esta fun recopilamos la info por 10 nsegundos, y se ejecuta en segundo plano:
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Seguimiento en segundo plano")
            .setContentText("El seguimiento esta activo")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .build()

        startForeground(1, notification)
//        Creamos un hilo para que se ejecute la tarea de seguimiento en segundo plano.
//        EN ESTE CASO ES CONTAR HASTA 10:
        Thread {
            for (i in 0..100) {
                Thread.sleep(1000)
                Log.d("TrackingService", "Seguimiento en segundo plano: $i")
            }
            stopSelf()
        }
            .start()


        return START_STICKY

    }



    private fun createNotificationChannel() {
        val name = "Canal de Seguimiento"
        val descriptionText = "Descripcion del Canal de Seguimiento"
        val importance = NotificationManager.IMPORTANCE_LOW
        val serviceChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(serviceChannel)
    }








}