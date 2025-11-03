package com.example.miadaptative

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//Este composable se refiere a un telefono movil:
@Composable
fun CompactLayout(products: List<String>) {
    LazyColumn(
//        Separacion vertical entre cada elem del LazyColumn:
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
// Recorremos la Lista products y generamos un Item por producto.
        // Luego en cada item obtenemos cada uno de los productos de la Lista:
        items(products.size){index ->
            ProductCard(name = products[index])

        }
    }


}