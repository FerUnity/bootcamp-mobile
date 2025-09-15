package com.example.proyectopersonal.ui.screens.medlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyectopersonal.R

@Composable
fun MedListFAB(navController: NavController) {
    ExtendedFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        onClick = {
            navController.navigate("add_contact")
        },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Agregar medicamento"
        )
        Text(
            text ="Agregar medicamento"
        )
    }
}