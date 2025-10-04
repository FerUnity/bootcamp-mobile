package com.example.proyectopersonal.model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices

//Esta clase va a permitir interactuar con el cliente FusedLocationProviderClient(), que permite obtener la Geolocalizacion.
// Esta clase entonces es invocada por el MapsViewModel, de clase que contiene la logica del ViewModel:

class LocationRepository(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") //Nos libera de pedir un permiso especial para la geolocalizacion
    fun getLastKnownLocation(
        onSuccess: (Location?) -> Unit, //Si se devuelve la ubicacion actual del dispositivo, sera exitoso
        onError: (Exception) -> Unit //Si no se logra obtener la ubicacion actual del dispositivo,
    //por falta de conexion, wifi o cualq motivo, se lanza una excepcion
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location -> onSuccess(location)}
            .addOnFailureListener { error -> onError(error) }
    }



}
