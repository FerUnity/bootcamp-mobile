package com.example.kmpnativo.model

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import com.example.kmpnativo.MainActivity

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

//Esta fun getPlatform() es llamamda desde el Greeting
actual fun getPlatform(): Platform = AndroidPlatform()

//Para obtener la ruta del external storage de la mobile app:
actual fun getUserHomeDir(): String = Environment.getExternalStorageDirectory().absolutePath

//Para obtener el nivel de bateria:
class AndroidBatteryLevel(private val context: Context) : BatteryLevel {
    override fun getBatteryLevel(): Int? {
        val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level == -1 || scale == -1) {
            null //SI el nivwel o la escala de la BATT es -1, nopuede ser asi que ret nulo
        } else {
            (level.toFloat() / scale.toFloat() * 100).toInt() //Porcentaje de BATT
        }
    }

}

actual fun getSystemUserName(): String? {
//    Opcion 1: Que se reconozcqa como excepcion:
    throw UnsupportedOperationException("Not implemented on Android platform")

//    Opcion2: Que retorne nulo:
//    return null


}