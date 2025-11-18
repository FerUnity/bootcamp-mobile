package com.example.proyectopersonal.model.notificacionesModel

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

//LAs Workers son tareas programadas a realizarse en segundo plano, EN ESTE CASO SYNC DATOS:
//Esta fun se usa en la vista ExamplesScreen para iniciar el SyncWorker:

class SyncWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    //    Esta fun es la que se llama desde el btn del ExamplesScreen para iniciar el SyncWorker:
    override suspend fun doWork(): Result {
//        QUEREMOS SINCRONIZAR EL SERVICIO:
        Log.d("SyncWorker", "Sincronizacion realizada ${System.currentTimeMillis()}")//Con una fecha y hora.

        return Result.success()

    }

}