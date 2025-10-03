package com.example.proyectopersonal.ui.screens.IndexDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.components.IndexTopBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope

// Pantalla que muestra el DETALLE del producto,
// al pres el btn Detalle de la lista de productos.

@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val sanBorja = LatLng(-33.460859960868305, -70.64187263068847)
    var markerState by remember { mutableStateOf<MarkerState?>(MarkerState(position = sanBorja)) }
//    var markerState = rememberMarkerState(position = sanBorja) ESTA DEPRECADO
   val cameraPositionState = rememberCameraPositionState {
       position = CameraPosition.fromLatLngZoom(sanBorja, 15f)
   }
    Scaffold {innerPadding ->
        GoogleMap(
            modifier = Modifier
                .padding(innerPadding),
            cameraPositionState = cameraPositionState,
//            SI QUEREMOS QUE AL HACER TAP EN UN PTO CAMBIE LA POSICION EL MARCADOR que contene la posicion sanBorja:
            onMapClick = {latLng ->
//                Con una de las 2 siguientes formas podemos cambiar la posicion del marcador:
                markerState = MarkerState(position = latLng)
//                markerState?.position = latLng
            }

        ) {
            Marker(
                state = markerState!!,
                title = markerState!!.position?.toString()?: "Ubicacion seleccionada",
                snippet = markerState!!.position?.toString()?: "Marker en Ubicacion",
                draggable = true, //Marker arrastrable

                onInfoWindowClick = {
                    println("Hola 1")
                    false
                },
//                onMarkerClick = { marker ->
//                    println("Hola 2")
//                    false
//
//                },
                onClick = {
                    println("Hola 2")
                    false
                },
//                Quiero que al hacer un click largo sobre un punto, se imprima esa ubicacion en el LogCat
                onInfoWindowLongClick = {
                    println("Lat: ${it.position.latitude}, Lng: ${it.position.longitude}")
                }
            )
            Polyline(
                points = listOf(
                    LatLng(-33.460859960868305, -70.64187263068847),
                    LatLng(-33.460859960868305, -70.64187263068847),
                    LatLng(-33.460859960868305, -70.64187263068847)
                ),
                color = MaterialTheme.colorScheme.primary


            )
           /* Circle(
                center = sanBorja,
                radius = 50000.0,
                fillColor = MaterialTheme.colorScheme.onPrimary,//Color del area de circulo
                strokeColor = MaterialTheme.colorScheme.primary,// Color del borde del circulo
            )*/

            Polygon(
                points = listOf(
                    LatLng(-33.460859960868305, -70.64187263068847),
                    LatLng(-33.460859960868305, -70.64187263068847),
                    LatLng(-33.460859960868305, -70.64187263068847)
                ),
                strokeColor = MaterialTheme.colorScheme.primary,

            )
        }
    }

    }

//    Box(modifier = Modifier.fillMaxSize()) {
//        GoogleMapView(
//            modifier = Modifier.matchParentSize(),
//            cameraPositionState = cameraPositionState
//        )
//    }


//@Composable
//fun GoogleMapView(
//    modifier: Modifier = Modifier,
//    cameraPositionState: CameraPositionState = rememberCameraPositionState()
//) {
//    GoogleMap(
//        modifier = modifier,
//        cameraPositionState = cameraPositionState
//    )
//}

@Composable
fun IndexDetailForm(id: String?, modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        Text("Detalle del Producto")
        Button(
            onClick = {
                //Con este btn Volver regresamos a la pantalla anterior:
                //IndexForm.kt
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                // en Color.kt y al tema en Theme.kt:
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(stringResource(R.string.button_back))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexDetailScreen(navController: NavHostController, id: String?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            //Usamos la fun composable IndexTopBar, de la clase IndexTopBar.kt:
            IndexTopBar(
                navController, drawerState, scope, stringResource(R.string.app_name))
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
            MapScreen(
                modifier = Modifier
                    .padding(innerPadding)

            )
            IndexDetailForm(
                id,
                modifier = Modifier
                    .padding(innerPadding),
                navController
            )







    }


}