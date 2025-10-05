package com.example.proyectopersonal.ui.screens.appMapaDesplegable

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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

//Ruta: map_search
//Este archivo sirve para ubicar una direccion en el Mapa
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
    val cameraPosition by mapsViewModel.cameraPosition.collectAsState()
    var address by remember { mutableStateOf("") }
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var markerState by remember { mutableStateOf<MarkerState?>(MarkerState(position = latLng)) }

    //    Codigo para pedir permisos de geolacalizacion al usuario
    var showDialog: Boolean by remember { mutableStateOf(true) }
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
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(3f),
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                )
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        mapsViewModel.getCoordinatesFromAddress(address)
                    }
                ) {
                    Text("Ver")
                }
            }
            cameraPosition?.let { camPos ->
                latLng = camPos.position.target
                markerState = MarkerState(position = latLng)
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = camPos,
                    properties = MapProperties(
                        mapType = MapType.NORMAL
                    ),
//                    Si hagi click en cualkq punto de mapa que se mueva la camara y se asigne un marker a ese punto:
                    onMapClick = {
                        latLng = it
                        markerState = MarkerState(position = latLng)
                        mapsViewModel.setCameraPosition(latLng)
                    }
                ) {
                    markerState?.let { marker ->
                        Marker(
                            state = marker,
                            title = mapsViewModel.getAddressFromCoordinates(latLng),
                            snippet = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}",
                            draggable = false
                        )
                    }
                }
            } ?: run {
                Text(
                    text = "Cargando ubicación...",
                    modifier = Modifier
                        .fillMaxWidth(1f)
                )
            }
        }
    }

} //Cierre fun AppConMapaDesplegable()