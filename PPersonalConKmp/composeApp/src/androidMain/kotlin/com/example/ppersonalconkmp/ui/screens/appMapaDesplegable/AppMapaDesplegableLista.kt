package com.example.proyectopersonal.ui.screens.appMapaDesplegable


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

//Este archivo sirve para buscar en el Mapa los hospitales registrados en la red Hospitalaria
// rep la vista o view de la pantalla que muestra el Mapa
//Falta vincularlo al ViewModel como fuente de datos. O sea como intermediario entre la vista y el modelo.
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMapaDesplegableLista(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    //    Codigo para pedir permisos de geolacalizacion al usuario
    var showDialog: Boolean by remember { mutableStateOf(true) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
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
            onDismiss = { showDialog = false } //Si presiono fuera del cuadro de dialogo, que se cierre el cuadro de dialogo
        )
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
    var selectedLocation by remember {
        mutableStateOf<LatLng>(
            myPosition //Posicion inicial al abrir el Mapa
        )
    }
    var hospitalSeleccionado: Hospital? by remember { mutableStateOf(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLocation, 15f)
    }
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
                    value = hospitalSeleccionado?.name ?: "",
                    onValueChange = { hospitalSeleccionado?.name = it },
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
                    hospitales.forEach { hospital ->
                        DropdownMenuItem(
                            text = { Text(hospital.name) },
                            onClick = {
                                expandedMenu = false
                                selectedLocation = LatLng(hospital.lat, hospital.lon)
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(selectedLocation, 15f)
                                hospitalSeleccionado = hospital
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
            if (selectedLocation != null) {
//                Marker(state = MarkerState(position = selectedLocation!!))
                Marker(
                    state = MarkerState(position = selectedLocation!!),
                    title = MarkerState(position = selectedLocation!!).position.toString(),
//                    title = markerState?.position?.toString()?: "Ubicacion seleccionada",
                    snippet = hospitalSeleccionado?.name ?: "Hospital",
                    draggable = true //Que el marcador sea arrastrable
                    /*  onClick = {
                          println("Hola 1")
                          true
                      },
                      onInfoWindowClick = {
                          println("Hola 2")
                          true
                      }*/
                )
                /* Circle(
                     center = selectedLocation,
                     radius = 500.0,
                     fillColor = MaterialTheme.colorScheme.onPrimary,//Color del area de circulo
                     strokeColor = MaterialTheme.colorScheme.primary,// Color del borde del circulo
                     strokeWidth = 5f
                 )*/
            }
        } //Cierre del GoogleMap

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                .weight(1f)
        ){
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

} //Cierre fun AppConMapaDesplegable()







