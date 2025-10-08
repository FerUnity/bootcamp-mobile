package com.example.proyectopersonal.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.proyectopersonal.model.maps.GeoLocale
import com.example.proyectopersonal.model.maps.Rotation
import com.example.proyectopersonal.model.maps.UserUIState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Esta clase va a permitir interactuar con el cliente FusedLocationProviderClient(), que permite obtener la Geolocalizacion.
// Esta clase entonces es invocada por el MapsViewModel, de clase que contiene la logica del ViewModel:

class LocationRepository(private val context: Context) : SensorEventListener {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val _userLocation = MutableStateFlow<UserUIState>(UserUIState(
        position = GeoLocale(0.0, 0.0),
        rotation = Rotation()
    ))
    val userLocation: StateFlow<UserUIState> = _userLocation

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLastKnownLocation(
        onSuccess: (Location?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        Log.println(Log.INFO, "LocationRepository", "Getting Last Known Location")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                Log.println(Log.INFO, "LocationRepository", "Location: $location")
                _userLocation.value.position = GeoLocale(location.latitude, location.longitude)
                onSuccess(location)
            }
            .addOnFailureListener { error ->
                Log.println(Log.ERROR, "LocationRepository", "Error: $error")
                onError(error)
            }
    }

    fun registerSensorListener() {
        sensorManager.registerListener(
            this,
            rotationSensor,
            SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterSensorListener() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
        // Cambio de precisión en el sensor
    }

    override fun onSensorChanged(sensor: SensorEvent?) {
        if (sensor?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            val orientationAngles = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, sensor.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            val azimuthDegrees = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            _userLocation.value.rotation = Rotation(azimuthDegrees)
            Log.println(Log.INFO, "LocationRepository", "Rotation: $azimuthDegrees")
        }
    }
}