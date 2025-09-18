package com.example.indicadoresmvp.ui.screens.indexScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.indicadoresmvp.R
import com.example.indicadoresmvp.model.IndexViewModel
import com.example.indicadoresmvp.model.IndicadorInternacionalEnumeration
import com.example.indicadoresmvp.model.IndicadorNacionalEnumeration
import com.example.indicadoresmvp.ui.components.Destination
import kotlinx.coroutines.launch
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexForm(snackbarHostState: SnackbarHostState,
              innerPadding: PaddingValues,
              destination: Destination,
              indexModel: IndexViewModel = viewModel()
    //Creamos var indexModel de tipo IndexViewModel y el viewModel() es para que sea persistente
) {
    var expandedIndex by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val businessIndex by indexModel.businessIndex.collectAsState()
    var indexSelected by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        indexModel.indexType = destination.contentDescription

        if (destination == Destination.NAC) {
            Text(
                "Índices Nacionales",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        } else if (destination == Destination.INT) {
            Text(
                "Índices Internacionales",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }

        ExposedDropdownMenuBox(
            expanded = expandedIndex,
            onExpandedChange = { expandedIndex = !expandedIndex },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = indexSelected,
                onValueChange = { },
                readOnly = true,
                label = { Text(stringResource(R.string.business_index_text)) },
                trailingIcon = { TrailingIcon(expanded = expandedIndex) },
                isError = indexModel.indexErrorMessage != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
            )
            ExposedDropdownMenu(
                expanded = expandedIndex,
                onDismissRequest = { expandedIndex = false }
            ) {
                if (indexModel.indexType == "Nacionales") {
                    IndicadorNacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndex = false
                                indexSelected = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )
                    }
                } else {
                    IndicadorInternacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndex = false
                                indexSelected = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )
                    }
                }
            }
        }
        //        Para que el mmensaje aparezca de color rojo si hay error y bajo el TextField
//        a una distancia de 16dp hacia la izq:
        indexModel.indexErrorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

//        FECHA: DEBERIA SER FIJO CON LA FECHA DE HOY?
        TextField(
            value = indexModel.date,
            onValueChange = { indexModel.onDateChange(it) },
            label = { Text(stringResource(R.string.date_text)) },
            placeholder = { Text(stringResource(R.string.date_placeholder)) },
            isError = indexModel.dateErrorMessage != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
//        Para que el mmensaje aparezca de color rojo si hay error y bajo el TextField
//        a una distancia de 16dp hacia la izq:
        indexModel.dateErrorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


//        ACA DEBE IR UN SEGUNDO TEXTFIELD PARA INGRESAR LA CONVERSION a la moneda SOLICITADA:

//        Y CREO QUE UN TERCER TEXTFIELD PARA INGRESAR EL MONTO A CONVERTIR DE UNA MONEDA A OTRA:

//        OJO SE DEBE obtener el valor actual de cada moneda en dólares desde la API dada,
//        para luego hacer la transformación (la app debe hacer la transformación de la moneda, no el servicio).

//        Boton para obtener los indicadores desde la API con getIndex():
        Button(
            onClick = {
                val result = indexModel.validateForm()
                if (result.isSuccess) {
//                    Creo que debe obtenerse de la BD Local no de la API:
                    indexModel.getIndex()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("El formulario presenta errores")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.query_button)) //Consultar
        }

//        Si se cumplen las condiciones para que la consulta a la API salga bien,
//        Mostramos un texto con los datos del indicador consultado:
        if (businessIndex.codigo != "" && businessIndex.serie.isNotEmpty()) {
            Row {
                Text(
                    text = businessIndex.nombre,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = String.format("%,.2f", businessIndex.serie[0].valor),
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = businessIndex.unidad_medida,
                    modifier = Modifier.padding(16.dp)
                )
            }
//            Si no se encuentra la info del indicador, mostramos un mensaje de error:
        } else if (businessIndex.codigo != ""){
            Text(
                text = "Ha ocurrido un error al obtener los datos del servicio. " +
                        "Pruebe ingresando una fecha diferente o intente más tarde.",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}




//    Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            //En el cont de la Column armamos el formulario:
//
//
//            //Lista de indices disponibles esto es del negocio no de la pantalla, por ende va al viewModel::
//            //val indexOptions: List<String> = listOf("UF", "UTM", "UTA", "Dolar", "Euro")
//            //Var que recorre la lista de indices, tb es del negocio por ende tb va al viewModel:
//            //var selectedIndex: String by remember { mutableStateOf(indexOptions[0]) }
//
//            //PARA EL TIPO DE INDICE ECONOMICO: NACIONAL O INTERNACIONAL
//            // Creamos una primera lista desplegable, la var expanded es de la pantalla no del negocio:
//            var expandedIndexType: Boolean by remember { mutableStateOf(false) }
//            ExposedDropdownMenuBox(
//                expanded = expandedIndexType,
//                //Luego para que cambie de estado de abierto a cerrado,
//                // el menu desplegable con onExpandedChange:
//                onExpandedChange = { expandedIndexType = !expandedIndexType },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//
//            ) {
//                //Cont del ExposedDropdownMenuBox:
//                TextField(
////                    Inicialmente estara vacio el TextField del desplegable:
//                    value = " ",
//                    onValueChange = {},
//                    //No se puede escribir, solo aparece la opcion elegida:
//                    readOnly = true,
//                    label = { Text("Tipo de Indice Economico") },
//                    //Icono triangulo chico para desplegar el menu:
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIndexType) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
//                    // Importante para que funcione correctamente
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expandedIndexType,
//                    //Si pincho en cualq parte de la antalla que se cierre el menu desplegable:
//                    onDismissRequest = { expandedIndexType = false }
//                ) {
//                    //indexOptions es la lista de indices disponibles,
//                    // y por cada opcion de la lista hacemos un DropdownMenuItem:
//                    indexModel.indexTypeOptions.forEach { option ->
//                        DropdownMenuItem(
//                            text = { Text(option) },
//                            onClick = {
//                                expandedIndexType = false
//                                //En la var  indexModel.onIndexChange(option), guardamos la opcion elegida:
//                                indexModel.onIndexTypeChange(option)
//
//                            }
//                        )
//                    }
//                }
//
//
//            } //Cierre ExposedDropdownMenuBox
//
//            //PARA EL INDICE ECONOMICO: SEGUN SE SELECCIONO ANTEIORMENTE NACIONAL O INTERNACIONAL:
//            // Creamos una segunda lista desplegable, la var exanded es de la pantalla no del negocio:
//            var expandedIndex: Boolean by remember { mutableStateOf(false) }
//            ExposedDropdownMenuBox(
//                expanded = expandedIndex,
//                //Luego para que cambie de estado de abierto a cerrado,
//                // el menu desplegable con onExpandedChange:
//                onExpandedChange = { expandedIndex = !expandedIndex },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//
//            ) {
//                //Cont del ExposedDropdownMenuBox:
//                TextField(
//                    value = " ",
//                    onValueChange = {},
//                    //No se puede escribir, solo aparece la opcion elegida:
//                    readOnly = true,
//                    label = { Text("Indice Economico") },
//                    //Icono triangulo chico para desplegar el menu:
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIndex) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
//                    // Importante para que funcione correctamente
//                )
//
//                ExposedDropdownMenu(
//                    expanded = expandedIndex,
//                    //Si pincho en cualq parte de la antalla que se cierre el menu desplegable:
//                    onDismissRequest = { expandedIndex = false }
//                ) {
//                    //gatIndexOptions es la fun que devuelve la lista de indices Nacionales o Internacionales disponibles,
//                    // y por cada opcion de la lista hacemos un DropdownMenuItem:
//                    indexModel.getIndexOptions().forEach { option ->
//                        DropdownMenuItem(
//                            text = { Text(option) },
//                            onClick = {
//                                expandedIndex = false
//                                //En la var  indexModel.onIndexChange(option), guardamos la opcion elegida:
//                                indexModel.onIndexChange(option)
//
//                            }
//                        )
//                    }
//                }
//
//
//            } //Cierre ExposedDropdownMenuBox
//
//            //ERROR DE INDICE ECONOMICO VACIO:
//            // Creamos un mensaje emergente de error con la fun let y las var de error del IndexViewModel:
//            indexModel.indexErrorMessage?.let {
//                Text(
//                    text = it,
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(start = 16.dp)
//                )
//            }
//
//            //PARA LA FECHA
//            //Para ingresar la fecha ponemos un TextField, date tb es el del negocio por ende va al viewModel:
////            var date by remember { mutableStateOf("") }
//            TextField(
//                value = indexModel.date,
//                onValueChange = { indexModel.onDateChange(it) },
//                placeholder = { Text("Formato dd/mm/aaaa") },
//                label = { Text("Fecha") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            )
//
//            //ERROR DE FECHA O DATE:
//            // Creamos un mensaje emergente de error con la fun let y las var de error del IndexViewModel:
////            indexModel.indexErrorMessage?.let { errorMessage ->
////                Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)
////            }
//            indexModel.dateErrorMessage?.let {
//               Text(
//                   text = it,
//                   color = MaterialTheme.colorScheme.error,
//                   modifier = Modifier.padding(start = 16.dp)
//               )
//            }
//
//
//            //Boton para que una vez selecc el indicador economico, lo ingrese y nos lleve a la pantalla
//            //de consulta del detalle del Indice seleccionado segun la fecha agregada:
//            Button(
//                onClick = {
//                    //USamos la fun validateForm() del archivo IndexViewModel.kt para validar el llenado del form,
//                    // guardamos el resultadode la validacion en un valor:
//                    val result = indexModel.validateForm()
//                    if (result.isSuccess) {
//                        indexModel.getIndex()
//                    } else {
//                        scope.launch {
//                            snackbarHostState.showSnackbar("El formulario presenta errores")
//                        }
//
//                    }
//                    //Nos vamos a la sgte pantalla, asi:
//                    //Fijarse que se pasan los 2 param del formulario asi,
//                        // ${indexModel.index } y ${indexModel.date}"), asi:
////                    navController.navigate("index_detail/${indexModel.index }/${indexModel.date}")
//
//                          },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text("Consultar")
//
//            }
//
//        }   //Cierre cont Column()
//
//
//
//}