package com.example.proyectopersonal.model.maps

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.CameraPositionState.Companion.invoke
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerState

data class InfoMarker(
    var position: MarkerState,
    var title: String,
    var snippet: String,
    var visible: Boolean
)

data class MapUIState(
    var cameraPosition: CameraPositionState = CameraPositionState(
        CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 15f)),
    var infoMarker: InfoMarker?,
    var mapType: MapType = MapType.NORMAL
)
