package com.example.indicadoresmvp.ui.screens.indicadorlist

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
import com.example.indicadoresmvp.room.Indicador

@Composable
fun IndicadorItemComponent(indicador: Indicador, navController: NavController) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CutCornerShape(8.dp),
        border = CardDefaults.outlinedCardBorder(),
        onClick = {
            navController.navigate("edit_contact/${indicador.id}")
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
                    "Codigo",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.codigo,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Nombre",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.nombre,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Unidad de medida",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.unidad_medida,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Serie",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.serie.toString(),
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Imagen URL",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.imagenUrl,
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Valor",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.serie[0].valor.toString(),
                    modifier = Modifier.weight(2f)
                )
            }
            Row() {
                Text(
                    "Fecha",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    indicador.serie[0].fecha,
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}