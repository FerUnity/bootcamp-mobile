package com.example.proyectopersonal.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import com.example.proyectopersonal.model.sensores.GyroscopeSensorUIState
import com.example.proyectopersonal.model.sensores.LuxSensorUIState
import com.example.proyectopersonal.model.sensores.MagneticSensorUIState
import com.example.proyectopersonal.model.sensores.MotionSensorUIState
import com.example.proyectopersonal.model.sensores.ProximitySensorUIState
import com.example.proyectopersonal.model.sensores.StepSensorUIState
import kotlinx.coroutines.flow.MutableStateFlow


//Este es el viewModel que se encarga de escuchar los cambios de los sensores registrados.
//Aca estan las funciones que luego se llaman desde el archivo de la vista de los sensores: SensorView.kt
class SensorViewModel(application: Application): AndroidViewModel(application), SensorEventListener {

    //    Con la clase SensorManager, obtenemos la lista de todos los sensores del dispositivo:
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    //    Luego obtenemos el sensor de interes: acelerometro, proximidad, campo magnético, luz, giroscopio y contador de pasos
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    //    Creamos las 2 var que son iguales al final, donde se guardan los cambios de los sensores:
    private val _motionSensor = MutableStateFlow(MotionSensorUIState())
    val motionSensor = _motionSensor

    private val proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    private val _proximitySensor = MutableStateFlow(ProximitySensorUIState())
    val proximitySensor = _proximitySensor

    //    Creamos las 2 var que son iguales al final, donde se guardan los cambios de los sensores:
    private val magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private val _magneticSensor = MutableStateFlow(MagneticSensorUIState())
    val magneticSensor = _magneticSensor

    //    Creamos las 2 var que son iguales al final, donde se guardan los cambios de los sensores:
    private val lux = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    private val _luxSensor = MutableStateFlow(LuxSensorUIState())
    val luxSensor = _luxSensor

    //    Creamos las 2 var que son iguales al final, donde se guardan los cambios de los sensores:
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private val _gyroscopeSensor = MutableStateFlow(GyroscopeSensorUIState())
    val gyroscopeSensor = _gyroscopeSensor

    //    Creamos las 2 var que son iguales al final, donde se guardan los cambios de los sensores:
    private val stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private val _stepSensor = MutableStateFlow(StepSensorUIState())
    val stepSensor = _stepSensor

//    Fin obtencion del acceso a los sensores de interes.


    //En la sgte funcion: fun starListening(), registramos todos los sensores de interes para escuchar:
    //  acelerometro, proximidad, campo magnético, luz, giroscopio y contador de pasos
    fun starListening() {
        sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            proximity,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            magnetic,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            lux,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            gyroscope,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            this,
            stepCounter,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    //    En la sgte funcion: fun stopListening(), desregistramos todos los sensores de interes para escuchar:
    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    //    Las 2 sgtes fun son propias del sensorManager.registerListener(). Pero hay que redifinir su comportasmiento a nuestra necesidad,
//    por eso se usa override.
//    En esta fun onAccuracyChanged() se registra la precisión del sensor.
    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
    }

    //    La fun onSensorChanged() es la que detecta y captura los cambios en los sensores registrados,
//    esos cambios se capturan las variables _motionSensor, _proximitySensor, etc...
//    que son las variables de la data class MotionSensorUIState()
//    y actualiza los estados correspondientes,
//    creados en la data class MotionSensorUIState(), ProximitySensorUIState(), etc...
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            _motionSensor.value = MotionSensorUIState(
                x = event.values[0],
                y = event.values[1],
                z = event.values[2]
            )
        }
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            _proximitySensor.value = ProximitySensorUIState(
                distance = event.values[0]
            )
        }
        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            _magneticSensor.value = MagneticSensorUIState(
                x = event.values[0],
                y = event.values[1],
                z = event.values[2]
            )
        }
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            _luxSensor.value = LuxSensorUIState(
                lux = event.values[0]
            )
        }
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            _gyroscopeSensor.value = GyroscopeSensorUIState(
                x = event.values[0],
                y = event.values[1],
                z = event.values[2]
            )
        }
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            _stepSensor.value = StepSensorUIState(
                steps = event.values[0].toInt()
            )
        }
    }
}