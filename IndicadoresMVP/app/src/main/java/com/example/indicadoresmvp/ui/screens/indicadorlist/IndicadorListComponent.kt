package com.example.indicadoresmvp.ui.screens.indicadorlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.indicadoresmvp.model.IndexViewModel
import com.example.indicadoresmvp.repository.IndicadorRepository
import com.example.indicadoresmvp.room.AppDatabase
import com.example.indicadoresmvp.service.IndicadoresApiService

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun IndicadorListComponent(modifier: Modifier = Modifier, navController: NavController) {
    val db = remember { AppDatabase.getDatabase(navController.context) }
    val api = remember { IndicadoresApiService.ApiInstance.api }
    val repository = remember { IndicadorRepository(db.indicadorDao(), api) }
    /* val contactListViewModel: ContactListViewModel = viewModel(
        factory = ContactListViewModelFactory(repository)
    ) */
    val indexViewModel = IndexViewModel(navController.context)
//    el val indicadores se usa desde el viewModel para almacenar la lista de indicadores desde la API:
    val indicadores by indexViewModel.indicadores.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //    Luego recorremos la lista de indicadores(API) y usamos el LazyColumn para mostrar la lista de indicadores,
//    usando el recurso grafico CARD, que implementa el componente IndicadorItemComponent:
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(indicadores.size) { index ->
                val indicador = indicadores[index]
                IndicadorItemComponent(indicador = indicador, navController)
            }
        }

//    Boton para volver a la pantalla anterior:
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("VOLVER")
        }

    }

}