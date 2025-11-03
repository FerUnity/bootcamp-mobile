package com.example.miadaptative

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedLayout(products: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//   Aca tambien recorremos la Lista products, obtenemos cada uno de sus elementos
        //   y creamos una tarjeta para cada uno, con un ancho de 250 dp:
        products.forEach { product ->
            ProductCard(name = product, modifier = Modifier.width(250.dp))
        }
    }

}