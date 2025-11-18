package com.example.proyectopersonal.ui.screens.medlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.proyectopersonal.model.AppDatabase
import com.example.proyectopersonal.room.MedRepository
import com.example.ppersonalconkmp.services.MedApiService
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoViewModel


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MedListComponent(modifier: Modifier = Modifier, navController: NavController) {
//    Ahora obtenemos la base de datos:
    val db: AppDatabase = remember { AppDatabase.getDatabase(navController.context) }
    val api = remember { MedApiService.RetrofitInstance.api }
//    Y luego ref al repositorio:
    val repository = remember { MedRepository(db.productDao(), api) }
    /*val addMedicamentoViewModel: AddMedicamentoViewModel = viewModel(
        factory = AddMedicamentoViewModelFactory(repository)
    )*/
    val addMedicamentoViewModel = AddMedicamentoViewModel(navController.context )
    val medicamentos by addMedicamentoViewModel.medicamentos.collectAsState()


    LazyColumn(
        modifier = modifier
            .fillMaxSize())
    {
        items(medicamentos.size) { index ->
//   Por cada medicamento que encuentre en la lista de medicamentos en el repositorio,
//   que pinte una card de presentacion como se configura en el archivo MedItemComponent.kt
            val medicamento = medicamentos[index]
            MedItemComponent(medicamento = medicamento, navController = navController)
        }
    }


}