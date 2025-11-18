package com.example.proyectopersonal.ui.screens.appMapaDesplegable

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectopersonal.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppConMapaDesplegable(
    innerPadding: PaddingValues
) {
    /*val locations: List<LatLng> =
        listOf(LatLng(40.7128, -74.0060), LatLng(34.0522, -118.2437))*/
    var expandedMenu by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
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
                    value = "",
                    onValueChange = {},
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
                    DropdownMenuItem(
                        text = { Text("Nueva York") },
                        onClick = {
                            selectedLocation = LatLng(40.7128, -74.0060) // Nueva York
                            expandedMenu = false
                        })
                    DropdownMenuItem(
                        text = { Text("Los Ángeles") },
                        onClick = {
                            selectedLocation = LatLng(34.0522, -118.2437) // Los Ángeles
                            expandedMenu = false
                        })
                    DropdownMenuItem(
                        text = { Text("Mutual") },
                        onClick = {
                            selectedLocation = LatLng(-33.45679824546918, -70.70086389231263) // Mutual
                            expandedMenu = false
                        })
                    DropdownMenuItem(
                        text = { Text("San Borja") },
                        onClick = {
                            selectedLocation = LatLng(-33.46089576352979, -70.64177607116393) // San Borja
                            expandedMenu = false
                        })
                    DropdownMenuItem(
                        text = { Text("J J Aguirre") },
                        onClick = {
                            selectedLocation = LatLng(-33.41995001119858, -70.65311040388185) // J J Aguirre
                            expandedMenu = false
                        })
                    DropdownMenuItem(
                        text = { Text("San Juan De Dios") },
                        onClick = {
                            selectedLocation = LatLng(-33.44193957960366, -70.6790216576723) // San Juan De Dios
                            expandedMenu = false
                        })
                }

                //indexOptions es la lista de indices disponibles,
                // y por cada opcion de la lista hacemos un DropdownMenuItem:
                /*locations.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.toString()) },
                            onClick = {
                                expandedIndexType = false
                                //En la var  indexModel.onIndexChange(option), guardamos la opcion elegida:
                                indexModel.onIndexTypeChange(option.toString())

                            }
                        )
                    }*/
            }
        }


        // Mapa
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    selectedLocation ?: LatLng(0.0,0.0), 10f)
                    // Ubicación por defecto o seleccionada, Nivel de zoom

            }
        ) {
            if (selectedLocation != null) {
//                Marker(state = MarkerState(position = selectedLocation!!))
                Marker(
                    state = MarkerState(position = selectedLocation!!),
                    title = "Ubicación seleccionada",
                    snippet = "Presiona para cerrar"
                )
            }
        }
    } //Cierre column

} //Cierre fun AppConMapaDesplegable()







