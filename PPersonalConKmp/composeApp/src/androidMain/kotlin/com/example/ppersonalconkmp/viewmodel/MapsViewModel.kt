package com.example.proyectopersonal.viewmodel

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.GeocodingRepository
import com.example.proyectopersonal.model.LocationRepository
import com.example.proyectopersonal.model.maps.MapUIState
import com.example.proyectopersonal.model.maps.UserUIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Clase Intermediaria entre la vista y el repositorio de datos.
// Gestiona el estado de la interfaz de usuario, orquesta la logica de negocio y provee datos a la vista.
// Se llama a las fun del repositorio LocationRepository, segun lo que se necesite en la vista.
//O sea decide cuando consultar al Repositorio

class MapsViewModel(
    private val locationRepository: LocationRepository,
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {
    //    Definimos una pos para la camara de google maps, en cada momento, para ir detectando y guardando sus cambios,
    //    por eso es var.
    //    EL MutableStateFlow es para que tener un observador y pueda reaccionar a esos cambios.
    //    Con ? para que pueda ser null (save null):
//    private var _cameraPosition = MutableStateFlow<CameraPositionState?>(null)
    private var _mapState: MutableStateFlow<MapUIState> = MutableStateFlow(MapUIState(
        infoMarker = null
    ))

    //    Luego creamos una val para guardar esos cambios de la camara,
//    porque el repositorio pide usar val no var:
//    val cameraPosition: StateFlow<CameraPositionState?> = _cameraPosition
    val mapState: StateFlow<MapUIState> = _mapState

    init {
        locationRepository.registerSensorListener()
    }

//    Idem para userloc:
//    private var _userLocation = MutableStateFlow<LatLng?>(null)
//    val userLocation: StateFlow<LatLng?> = _userLocation




    //    Creamos una fun loadUserLocation() para que desde aqui obtengamos la pos de la camara, por ende del usuario,
//    invocando la fun del repository de la clase LocationRepository, que hace eso.
//    Esta fun loadUserLocation() se llama desde la vista o composable que la invoca: AppMapaListaConMVVM.kt
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun loadUserLocation(context: Context) {
        locationRepository.getLastKnownLocation(
            onSuccess = { location ->
//                let es una función de alcance que se utiliza para ejecutar un bloque de código
                // en un objeto(location en este caso) si este no es nulo.
                Log.println(Log.INFO, "MapsViewModel", "Location: $location")
                //SI la ubicacion obtenida por la fun getLastKnownLocation no es nula la guardamos en la var
//                _cameraPosition.value y por ende  en cameraPosition:
                location?.let {
                    setCameraPosition(LatLng(it.latitude, it.longitude))
                }
            },
            onError = { exception ->
                Log.println(Log.ERROR, "MapsViewModel", "Error: $exception")
                exception.printStackTrace()
                //Para mostrar si hubo errores en la fun getLastKnownLocation
            }
        )

    }

    fun getActualUserLocation(): StateFlow<UserUIState> {
        return locationRepository.userLocation
    }

    //    fun para obtener coordenadas desde una direccion dada: Ademas la camara se movera a ese punto.
    fun getCoordinatesFromAddress(address: String) {
        val latLng = geocodingRepository.getCoordinatesFromAddress(address)
        if (latLng != null) {
            _mapState.value?.cameraPosition = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(latLng, 15f)
            )
        }
    }

    fun getCoordinatesFromAddress2(address: String): LatLng {
        val latLng = geocodingRepository.getCoordinatesFromAddress(address)
        return latLng?: LatLng(0.0,0.0)

    }

    fun getAddressFromCoordinates(latLng: LatLng) : String {
        val address = geocodingRepository.getAddressFromCoordinates(latLng.latitude, latLng.longitude)
        return address?: ""
    }

    fun setCameraPosition(latLng: LatLng) {
        val userLocation = getActualUserLocation().value
        Log.d("MapsViewModel", "Rotation: ${userLocation.rotation.orientation}")
        _mapState.value?.cameraPosition = CameraPositionState(
            CameraPosition.builder()
                .target(latLng)
                .zoom(15f)
                .bearing(userLocation.rotation.orientation)
                .tilt(45f)
                .build()
        )
    }

    fun getCameraPosition(): CameraPositionState? {
        return _mapState.value.cameraPosition
    }
}

