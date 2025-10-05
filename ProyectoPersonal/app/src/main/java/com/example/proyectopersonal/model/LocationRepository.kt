package com.example.proyectopersonal.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationServices

//Esta clase va a permitir interactuar con el cliente FusedLocationProviderClient(), que permite obtener la Geolocalizacion.
// Esta clase entonces es invocada por el MapsViewModel, de clase que contiene la logica del ViewModel:

class LocationRepository(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLastKnownLocation(
        onSuccess: (Location?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        Log.println(Log.INFO, "LocationRepository", "Getting Last Known Location")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                Log.println(Log.INFO, "LocationRepository", "Location: $location")
                onSuccess(location)
            }
            .addOnFailureListener { error ->
                Log.println(Log.ERROR, "LocationRepository", "Error: $error")
                onError(error)
            }
    }


}
