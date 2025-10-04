package com.example.proyectopersonal.model

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.Locale


//Clase para convertir una direccion a coordenadas y viceversa:

class GeocodingRepository(private val context: Context) {
    //    Para obtener la config propia del dispositivo respecto a como poner las direcciones,
    //    por ej si esta en ingles, hay que poner las direcciones en ingles, etc.
    private val geocoder = Geocoder(context, Locale.getDefault())

    //    fun para obtener las coordebadas desde una direccion:
    fun getCoordinatesFromAddress(address: String): LatLng? {
//        llamamos a la fun de la clase Geocoder que nos devuelve una lista de coordenadas desde una direccion.
        //        el maxRes[1] es para que devuelva solo una coordenada, la mejor de todas las posibles:
        val results = geocoder.getFromLocationName(address, 1)
        return if (!results.isNullOrEmpty()) {
            LatLng(results[0].latitude, results[0].longitude)
        } else {
            null
        }
    }

//    fun para obtener la direccion desde las coordenadas:
    fun getAddressFromCoordinates(lat: Double, lng: Double): String? {
    //        llamamos a la fun de la clase Geocoder que nos devuelve una lista de direcciones:
        val results = geocoder.getFromLocation(lat, lng, 1)
        return if (!results.isNullOrEmpty()) {
            results[0].getAddressLine(0)
        } else {
            null
        }

    }

}
