package com.example.indicadoresmvp.ui.screens.indicadornew

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.indicadoresmvp.R
import com.example.indicadoresmvp.model.IndexViewModel
import com.example.indicadoresmvp.repository.IndicadorRepository
import com.example.indicadoresmvp.room.AppDatabase
import com.example.indicadoresmvp.room.Indicador
import com.example.indicadoresmvp.service.IndicadoresApiService

//Este archivo lo usamos para agregar un nuevo indicador a la BD local:
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AddIndicadorForm(modifier: Modifier = Modifier, navController: NavController) {
    val db = remember { AppDatabase.getDatabase(navController.context) }
    val api = remember { IndicadoresApiService.ApiInstance.api }
    val repository = remember { IndicadorRepository(db.indicadorDao(), api) }
    /* val contactListViewModel: ContactListViewModel = viewModel(
        factory = ContactListViewModelFactory(repository)
    ) */
    val indexViewModel = IndexViewModel(navController.context)
    var indicadorCodigo by remember { mutableStateOf("") }
    var indicadorNombre by remember { mutableStateOf("") }
    var indicadorUnidadMedida by remember { mutableStateOf("") }
    var indicadorSerie by remember { mutableStateOf("") }
    var indicadorValor by remember { mutableStateOf("") }
    var indicadorFecha by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            value = indicadorCodigo,
            onValueChange = { indicadorCodigo = it },
            label = {
                Text(stringResource(R.string.codigo_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = indicadorNombre,
            onValueChange = { indicadorNombre = it },
            label = {
                Text(stringResource(R.string.nombre_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = indicadorUnidadMedida,
            onValueChange = { indicadorUnidadMedida = it },
            label = {
                Text(stringResource(R.string.unidad_medida_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = indicadorSerie,
            onValueChange = { indicadorSerie = it },
            label = {
                Text(stringResource(R.string.serie_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()

        )
        TextField(
            value = indicadorValor,
            onValueChange = { indicadorValor = it },
            label = {
                Text(stringResource(R.string.valor_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        TextField(
            value = indicadorFecha,
            onValueChange = { indicadorFecha = it },
            label = {
                Text(stringResource(R.string.fecha_field))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )


//        Luego de llenar el formulario con los datos,
//        presionamos el btn para agregar un nuevo indicador a la BD local,
//        usando la fun addIndicador():
        Button(
            onClick = {
                indexViewModel.addIndicador(
                    Indicador(
                        id = null,
                        codigo = indicadorCodigo,
                        nombre = indicadorNombre,
                        unidad_medida = indicadorUnidadMedida,
                        serie = listOf(Indicador.Serie(indicadorValor.toDouble(), indicadorFecha),),
                        imagenUrl = ""
                    ),
                    navController.context
                )
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.add_indicador),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}