package com.example.proyectopersonal.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FAButton() {
    //ExtendedFloatingActionButton o LargeFloatingActionButton:
    ExtendedFloatingActionButton(
        containerColor = Color.Blue,
        contentColor = Color.White,
        onClick = { /*TODO*/ }
    ) {
        //Cuerpo boton
        Icon(
            //Icono +:
            imageVector = Icons.Filled.Add,
            contentDescription = "Agregar",
            //Pinto amarillo el color del icono +:
            tint = Color.Yellow,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text("Agregar")


    }
}