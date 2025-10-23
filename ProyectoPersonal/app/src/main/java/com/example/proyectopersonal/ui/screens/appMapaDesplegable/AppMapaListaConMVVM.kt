package com.example.proyectopersonal.ui.screens.appMapaDesplegable

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.GeocodingRepository
import com.example.proyectopersonal.model.LocationRepository
import com.example.proyectopersonal.model.maps.InfoMarker
import com.example.proyectopersonal.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

//Ruta: map_search
//Este archivo sirve para ubicar una direccion especifica en el Mapa
// Rep la vista o view de la pantalla que muestra el Mapa,
// pero usando el ViewModel como fuente de datos. O sea como intermediario entre la vista y el modelo.
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMapaListaConMVVM(
    innerPadding: PaddingValues,
    mapsViewModel: MapsViewModel = MapsViewModel(
        LocationRepository(LocalContext.current),
        GeocodingRepository(LocalContext.current)
    )
) {
//    Creamos una val de la pos de la camara que va a estar asociada al cameraPosition del ViewModel, asi.
    //    O sea no creamos esa var desde cero:
    val context = LocalContext.current
    val mapState by mapsViewModel.mapState.collectAsState()
    val userLocation = mapsViewModel.getActualUserLocation().collectAsState()

    var miPosicion = "Aldunate 1064"
    var address by remember { mutableStateOf(miPosicion) }
//    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var latLng by remember { mutableStateOf(mapsViewModel.getCoordinatesFromAddress2(address)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }


    //    Codigo para pedir permisos de geolacalizacion al usuario
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    )
    /* { isGranted: Boolean ->
         if (isGranted) {
             // Permission is granted. Continue the action or workflow in your
             // app.
             showDialog = false

         } else {
             showDialog = true
         }
     }
     if (showDialog) {
         LocationPermissionDialog(
             onRequestPermission = {
                 showDialog = false
                 requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
             },
             onDismiss = {
                 showDialog = false
             } //Si presiono fuera del cuadro de dialogo, que se cierre el cuadro de dialogo
         )
     }*/
    { isGranted ->
        if (isGranted) {
            mapsViewModel.loadUserLocation(context)
        } else {
            Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }


//    Luego llamamos ala fun del viewModel que nos permite cargar la ubicacion del usuario, de forma async, asi:
    /* LaunchedEffect(Unit) {
         mapsViewModel.loadUserLocation(context)
     }*/
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mapsViewModel.loadUserLocation(context)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                )
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
//                        Convertimos la direccion ingresada en un LatLng
                        latLng = mapsViewModel.getCoordinatesFromAddress2(address)
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            latLng,
                            15f
                        )
                    }
                ) {
                    Text("Ver")
                }
            }
            Log.d("MapsExample", "MapState: $mapState")
//            val cameraPosition = mapsViewModel.getCameraPosition()
//            var latLng by remember { mutableStateOf(mapsViewModel.getCoordinatesFromAddress2(address)) }
            /*val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(latLng, 15f)
            }*/

            /*   cameraPositionState.position = CameraPosition.fromLatLngZoom(
                   latLng,
                   15f
               )*/
            Log.d("MapsExample", "CameraPosition: ${cameraPositionState.position}")
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapType = mapState.mapType
                )
                /* onMapClick = { latLng ->
                     mapState.infoMarker = null
                     mapsViewModel.setCameraPosition(latLng)
                 },*/
                /*onMapLongClick = { latLng ->
                    val address = mapsViewModel.getAddressFromCoordinates(latLng)
                    mapState.infoMarker = InfoMarker(
                        position = MarkerState(position = latLng),
                        title = address,
                        snippet = "Lat: ${latLng.latitude}, Long: ${latLng.longitude}",
                        visible = true
                    )
                }*/
            ) {
                if (mapState.infoMarker != null) {
                    val marker = mapState.infoMarker
                    //    Log.d("MapsExample", "Marker: ${marker}")
                    /* Marker(
                         state = marker.position,
                         title = marker.title,
                         snippet = marker.snippet,
                         visible = marker.visible
                     )*/
                    Marker(
                        state = MarkerState(
                            position = mapsViewModel.getCoordinatesFromAddress2(address)
                        ),
                        title = marker?.title ?: "Hospital seleccionado",
                        snippet = marker?.snippet ?: "Direccion",
                        visible = marker?.visible ?: true
                    )
                }
                /*Marker(
                    state = MarkerState(
                        position = mapsViewModel.getCoordinatesFromAddress2(address)
                    ),
                    title = mapState.infoMarker?.title?: "Hospital seleccionado",
                    snippet =  mapState.infoMarker?.snippet?: "Direccion"
                )*/
            }
        }
    }
} //Cierre fun AppConMapaDesplegable()