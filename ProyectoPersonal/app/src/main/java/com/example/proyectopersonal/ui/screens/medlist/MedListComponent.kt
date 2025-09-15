package com.example.proyectopersonal.ui.screens.medlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.proyectopersonal.model.AppDatabase
import com.example.proyectopersonal.model.ProductData
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoViewModel


@Composable
fun MedListComponent(modifier: Modifier = Modifier, navController: NavController) {
   var addMedicamentoViewModel: AddMedicamentoViewModel = remember {
       AddMedicamentoViewModel(navController.context)
   }
    val medicamentos: List<ProductData> = addMedicamentoViewModel.getMedicamentos()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(medicamentos.size) { index ->
//   Por cada medicamento que encuentre en la lista de medicamentos,
//   que pinte una card de presentacion como se muestra en el archivo MedItemComponent.kt
            val medicamento = medicamentos[index]
            MedItemComponent(medicamento = medicamento, navController = navController)
        }
    }


}