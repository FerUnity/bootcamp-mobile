package com.example.proyectopersonal.ui.screens.medlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.ProductData

//Esta es solo la vista de una card de presentacion de los medicamentos que se llama desde MedListComponent.kt
@Composable
fun MedItemComponent(medicamento: ProductData, navController: NavController) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CutCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder(),
//        Si toco la tarjeta que se vaya a la ventana de editar medicamento (mededit), que aun no esta activa
        onClick = {
            navController.navigate("edit_medicamento/${medicamento.id}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row() {
                Text(
//                    "nombre del medicamento: ",
                    stringResource(R.string.name_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.nombre,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
//                    "marca del medicamento: ",
                    stringResource(R.string.brand_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.marca,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
//                    "descripcion del medicamento: ",
                    stringResource(R.string.description_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.descripcion,
                    modifier = Modifier.weight(2f)
                )
            }

            Row() {
                Text(
//                    "precio del medicamento: ",
                    stringResource(R.string.price_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.precio.toString(),
                    modifier = Modifier.weight(2f)
                )
            }

            Row() {
                Text(
//                    "categoria del medicamento: ",
                    stringResource(R.string.category_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.categoria,
                    modifier = Modifier.weight(2f)
                )
            }

            Row() {
                Text(
//                    "lista del medicamento: ",
                    stringResource(R.string.medlist_label),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    medicamento.medListId.toString(),
                    modifier = Modifier.weight(2f)
                )
            }

        }
    }
}