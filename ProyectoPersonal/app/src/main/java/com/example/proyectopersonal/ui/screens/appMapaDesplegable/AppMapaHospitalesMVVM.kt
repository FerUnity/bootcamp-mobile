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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.Direccion
import com.example.proyectopersonal.model.GeocodingRepository
import com.example.proyectopersonal.model.LocationRepository
import com.example.proyectopersonal.model.direcciones
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
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMapaHospitalesMVVM(
    navController: NavHostController,
    innerPadding: PaddingValues,
    mapsViewModel: MapsViewModel = MapsViewModel(
        LocationRepository(LocalContext.current),
        GeocodingRepository(LocalContext.current)
    )
) {
    val context = LocalContext.current
    //    Codigo para pedir permisos de geolacalizacion al usuario
    var showDialog: Boolean by remember { mutableStateOf(true) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    )
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

//    Codigo del cliente que rep la clase FusedLocationProviderClient(), que permite la Geolocalizacion
//    y que implica una val fuseLocationClient e importar libs.
    //Posicion inicial al abrir el Mapa: Aula Magna Usach por def:
    var myPosition = LatLng(-33.44972844335567, -70.68670771534424)
    val fuseLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    if (ActivityCompat.checkSelfPermission(
            LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fuseLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                println("Lat: ${location.latitude}, Lng: ${location.longitude}")
                myPosition = LatLng(location.latitude, location.longitude)
            }
        }

    }
//    Para actualizar la posicion actual del dispositivo cada 5 segundos (Fallas):
    /* val locationRequest = LocationRequest.Builder(
         Priority.PRIORITY_HIGH_ACCURACY, 5000 //cada 5 seg
     ).build()
     val locationCallback = object: LocationCallback(){
         override fun onLocationResult(result: LocationResult) {
            for(location in result.locations){
                Log.d("Location", "Lat: ${location.latitude}, Lng: ${location.longitude}")
            }
         }
     }

     fuseLocationClient.requestLocationUpdates(
         locationRequest,
         locationCallback,
         null,
         Looper.getMainLooper()
     )*/


    //    Fin codigo para pedir permisos de geolacalizacion al usuario.

//    Codigo para usar mapas
    /*val locations: List<LatLng> =
        listOf(LatLng(40.7128, -74.0060), LatLng(34.0522, -118.2437))*/
    var expandedMenu by remember { mutableStateOf(false) }
    var miPosicion = "Aldunate 1064"
    var selectedAddress by remember { mutableStateOf(miPosicion) }
    /* var selectedLocation by remember {
         mutableStateOf<LatLng>(
             myPosition //Posicion inicial al abrir el Mapa
         )
     }*/
    var direccionSeleccionada: Direccion? by remember { mutableStateOf(null) }
//    val cameraPosition by mapsViewModel.cameraPosition.collectAsState()
    var latLng by remember { mutableStateOf(mapsViewModel.getCoordinatesFromAddress2(selectedAddress)) }
//    var markerState by remember { mutableStateOf<MarkerState?>(MarkerState(position = latLng)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }
    /* val cameraPositionState = rememberCameraPositionState {
         position = CameraPosition.fromLatLngZoom(mapsViewModel.getCoordinatesFromAddress(selectedAddress), 15f)
     }*/
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true, //Botones + y -
                zoomGesturesEnabled = true,
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true,
                tiltGesturesEnabled = true,
                compassEnabled = true,
                mapToolbarEnabled = true,
                myLocationButtonEnabled = true
            )
        )
    } //Fin codigo para usar mapas


    // Para almacenar la ubicación seleccionada a mostrar en el MAPA

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            //Para evitar que el teclado tape los componenetes en la pantalla:
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para abrir el menú
        /*    Button(onClick = { expandedMenu = true }) {
                Text("Mostrar Lugares")
            }*/

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                .weight(1f)
        ) {

            ExposedDropdownMenuBox(
                expanded = expandedMenu,
                //Luego para que cambie de estado de abierto a cerrado,
                // el menu desplegable con onExpandedChange:
                onExpandedChange = { expandedMenu = !expandedMenu },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            {
                //Cont del ExposedDropdownMenuBox:
                // Aca ira la Localizacion elegida enn el MAPA (Hospital):
                TextField(
                    value = direccionSeleccionada?.name ?: "",
                    onValueChange = { direccionSeleccionada?.name = it },
                    //No se puede escribir, solo aparece la opcion elegida:
                    readOnly = true,
                    label = { Text(stringResource(R.string.localization_label)) },
                    //Icono triangulo chico para desplegar el menu:
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMenu) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                    // Importante para que funcione correctamente
                )

//                ESTE MENU DESPLEGABLE TENDRIA QUE CONTENER LOS HOSPITALES CON SUS
//                LOCALIZACIONES EN EL MAPA: LatLng():
                ExposedDropdownMenu(
                    expanded = expandedMenu,
                    //Si pincho en cualq parte de la antalla que se cierre el menu desplegable:
                    onDismissRequest = { expandedMenu = false }
                ) {
                    // Opciones del menú
                    direcciones.forEach { direccion ->
                        DropdownMenuItem(
                            text = { Text(direccion.name) },
                            onClick = {
                                expandedMenu = false
                                selectedAddress = direccion.address
                                latLng = mapsViewModel.getCoordinatesFromAddress2(selectedAddress)
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                    latLng,
                                    15f
                                )
//                                mapsViewModel.setCameraPosition(mapsViewModel.getCoordinatesFromAddress2(selectedAddress))
                                /*cameraPosition?.position = CameraPosition.fromLatLngZoom(
                                    mapsViewModel.getCoordinatesFromAddress2(selectedAddress),
                                    15f
                                )*/
                                direccionSeleccionada = direccion

                            }
                        )
                    }

                }

            }
        }

//        var markerState by remember { mutableStateOf<MarkerState?>(MarkerState(position = selectedLocation)) }
        // Para mostrar el mapa con la ubicación seleccionada:
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            uiSettings = uiSettings, //Setteo de habiltar o deshabilitar func del mapa: zoom, mover, etc
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
//                mapType = MapType.HYBRID
//                mapType = MapType.SATELLITE
//                mapType = MapType.TERRAIN
                mapType = MapType.NORMAL
            ),
//            Si hacemos click sobre cualq punto de mapa se mueve el marcador markerState que contiene la ubicacion seleccionada,
//            a esa ubicacion:

            /*onMapClick = {latLng ->
//                Probar con una de las 2 formas:
//                selectedLocation = latLng
//                markerState = MarkerState(position = latLng)
                selectedLocation = latLng
//                hospitalSeleccionado = null
            },*/

//            Quiero que al hacer un click largo sobre un punto, se imprima esa ubicacion en el LogCat
            onMapLongClick = { latLng ->
                println("Lat: ${latLng.latitude}, Lng: ${latLng.longitude}")
            }

        ) {
            Marker(
                state = MarkerState(
                    position = mapsViewModel.getCoordinatesFromAddress2(selectedAddress)
                ),
                title = direccionSeleccionada?.name ?: "Hospital seleccionado",

                snippet = direccionSeleccionada?.address ?: "Direccion"
            )

            /* Circle(
                 center = selectedLocation,
                 radius = 500.0,
                 fillColor = MaterialTheme.colorScheme.onPrimary,//Color del area de circulo
                 strokeColor = MaterialTheme.colorScheme.primary,// Color del borde del circulo
                 strokeWidth = 5f
             )*/

        } //Cierre del GoogleMap


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                .weight(1f)
        ) {
            Button(
                onClick = {
                    navController.navigate("map_search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    // en Color.kt y al tema en Theme.kt:
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(text = "Buscar direccion en Mapa")
            }

        }

    } //Cierre column


}  //Cierre fun AppMapaHospitalesMVVM()