package com.example.proyectopersonal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.proyectopersonal.model.GeocodingRepository
import com.example.proyectopersonal.model.LocationRepository
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Clase Intermediaria entre la vista y el repositorio de datos.
// Gestiona el estado de la interfaz de usuario, orquesta la logica de negocio y provee datos a la vista.
// Se llama a las fun del repositorio LocationRepository, segun lo que se necesite en la vista.
//O sea decide cuando consultar al Repositorio

class MapsViewModel(private val locationRepository: LocationRepository, private val geocodingRepository: GeocodingRepository) : ViewModel() {
    //    Definimos una pos para la camara de google maps, en cada momento, para ir detectando y guardando sus cambios,
    //    por eso es var.
    //    EL MutableStateFlow es para que tener un observador y pueda reaccionar a esos cambios.
    //    Con ? para que pueda ser null (save null):
    private var _cameraPosition = MutableStateFlow<CameraPositionState?>(null)

    //    Luego creamos una val para guardar esos cambios de la camara,
//    porque el repositorio pide usar val no var:
    val cameraPosition: StateFlow<CameraPositionState?> = _cameraPosition

    //    Creamos una fun loadUserLocation() para que desde aqui obtengamos la pos de la camara, por ende del usuario,
//    invocando la fun del repository de la clase LocationRepository, que hace eso.
//    Esta fun loadUserLocation() se llama desde la vista o composable que la invoca:
    fun loadUserLocation() {
        locationRepository.getLastKnownLocation(
            onSuccess = { location ->
//                let es una función de alcance que se utiliza para ejecutar un bloque de código
                // en un objeto(location en este caso) si este no es nulo.
                //SI la ubicacion obtenida por la fun getLastKnownLocation no es nula la guardamos en la var
//                _cameraPosition.value y por ende  en cameraPosition:
                location?.let {
                    _cameraPosition.value = CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(
                            LatLng(location.latitude,location.longitude),
                            15f
                        )
                    )
                }
            },
            onError = {
                _cameraPosition.value = null
                it.printStackTrace() //Para mostrar si hubo errores en la fun getLastKnownLocation
            }
        )

    }

//    fun para obtener coordenadas desde una direccion dada: Ademas la camara se movera a ese punto.
    fun getCoordinatesFromAddress(address: String): LatLng {
        val latLng = geocodingRepository.getCoordinatesFromAddress(address)
//    Qie ret el valorpedidi de latLng. Si no se obtiene que ret las coord (0,0):
       return latLng?: LatLng(0.0,0.0)



    }


}