package com.example.indicadoresmvp.ui.screens.indexScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import com.example.indicadoresmvp.MainActivity
import com.example.indicadoresmvp.R
import com.example.indicadoresmvp.model.IndexViewModel
import com.example.indicadoresmvp.model.IndicadorInternacionalEnumeration
import com.example.indicadoresmvp.model.IndicadorNacionalEnumeration
import com.example.indicadoresmvp.ui.components.Destination
import kotlinx.coroutines.launch


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexForm(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    innerPadding: PaddingValues,
    destination: Destination,
//    indexModel: IndexViewModel = viewModel()
    indexModel: IndexViewModel = MainActivity.indexViewModel
    //Creamos var indexModel de tipo IndexViewModel y el viewModel() es para que sea persistente
) {
    var expandedIndex by remember { mutableStateOf(false) }
    var expandedIndexB by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val businessIndexA by indexModel.businessIndexA.collectAsState()
    val businessIndexB by indexModel.businessIndexB.collectAsState()

//    Para la primera divisa
    var indexSelectedA by remember { mutableStateOf("") }

    //    Para la segunda divisa
    var indexSelectedB by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
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


//        PRIMERA DIVISA A CONVERTIR
        ExposedDropdownMenuBox(
            expanded = expandedIndex,
            onExpandedChange = { expandedIndex = !expandedIndex },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = indexSelectedA,
                onValueChange = { },
                readOnly = true,
//                label = { Text(stringResource(R.string.business_index_text)) },
                label = { Text("Divisa 1: A convertir") },
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
//                Si selecciono Nacionales me muestra opt de la Lista: IndicadorNacionalEnumeration
                if (indexModel.indexType == "Nacionales") {
                    IndicadorNacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndex = false
//                                Le asignamos a la var indexSelectedA el nombre de la opcion seleccionada,
//                                por eso queda en el value del TextField arriba:
                                indexSelectedA = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )

                    }
//                      Si selecciono Internacionales me muestra opt de la Lista: IndicadorInternacionalEnumeration
                } else {
                    IndicadorInternacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndex = false
                                indexSelectedA = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )
                    }
                }

            }
            //           Obtenemos los valores de la API con getIndex() de la primera Divisa
//            indexModel.getIndex()

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


        //UN TEXTFIELD PARA INGRESAR EL MONTO A CONVERTIR DE LA PRIMERA MONEDA A LA SEGUNDA:
        var monto by remember { mutableStateOf("") }
        TextField(
            value = monto,
//            Es fundamental poner asi el onValueChange sino no se llena el TextField:
            onValueChange = { monto = it },
            label = { Text("Monto a convertir") },
            placeholder = { Text("Ingrese monto a comvertir") },
//            isError = indexModel.dateErrorMessage != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
//        Para que el mmensaje aparezca de color rojo si hay error y bajo el TextField
//        a una distancia de 16dp hacia la izq:
//        indexModel.dateErrorMessage?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }


        //ACA DEBE IR UN SEGUNDO TEXTFIELD PARA INGRESAR LA DIVISA A LA QUE SE QUIERE CONVERTIR la moneda ORIGINAL:
        ExposedDropdownMenuBox(
            expanded = expandedIndexB,
            onExpandedChange = { expandedIndexB = !expandedIndexB },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = indexSelectedB,
                onValueChange = { },
                readOnly = true,
//                label = { Text(stringResource(R.string.business_index_text)) },
                label = { Text("Divisa 2: A la que se requiere convertir") },
                trailingIcon = { TrailingIcon(expanded = expandedIndexB) },
                isError = indexModel.indexErrorMessage != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
            )
            ExposedDropdownMenu(
                expanded = expandedIndexB,
                onDismissRequest = { expandedIndexB = false }
            ) {
                if (indexModel.indexType == "Nacionales") {
                    IndicadorNacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndexB = false
                                indexSelectedB = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )
                    }
                } else {
                    IndicadorInternacionalEnumeration.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.nombre) },
                            onClick = {
                                expandedIndexB = false
                                indexSelectedB = option.nombre
                                indexModel.onIndexChange(option.codigo)
                            }
                        )
                    }
                }
            }
//            Obtenemos los valores de la API con getIndex() de la segunda Divisa
//            indexModel.getIndex()
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

//        FECHA: DEBERIA SER FIJO CON LA FECHA DE HOY:
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

//        OJO SE DEBE obtener el valor actual de cada moneda en dólares desde la API dada,
//        para luego hacer la transformación (la app debe hacer la transformación de la moneda, no el servicio).


        Button(
            onClick = {
                val result = indexModel.validateForm()
                if (result.isSuccess) {
//             Se obtiene el valor de la divisa1 ingresada segun fecha, desde la API externa:
                    // Con la fun getIndex() que devuelve como resultado: businessIndex: StateFlow<Indicador>:
                    indexModel.getIndex(indexSelectedA)
//                        Se obtiene el valor del indicador divisa2, desde la BD local:
                    indexModel.getIndicador(indexSelectedB)

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
            Text("Calcular")
//            Text(stringResource(R.string.query_button)) //Consultar
        }


//            Boton 2: SE CAE REVISAR:
        //        Boton para mostrtar la lisa de indicadores obtenidas desde la API:
        /*            Button(
                        onClick = {
                            navController.navigate("indicador_Api_list")
                            //TOAST: Mensaje corto indep de la activity, que no interactua con el usuario
                            val text = "Obteniendo indicadores desde la API"
                            val duration: Int = Toast.LENGTH_LONG
                            Toast.makeText(navController.context, text, duration).show()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text("Listado de indicadores de API")
                    }*/

//        Si se cumplen las condiciones para que la consulta a la API salga bien,
//        Mostramos un texto con los datos del indicador consultado:


        if (businessIndexA.codigo != "" && businessIndexA.serie.isNotEmpty()
            && businessIndexB.codigo != "" && businessIndexB.serie.isNotEmpty()
        ) {
            //        CALCULAMOS LOS VALORES Y HACEMOS LA CONVERSION ENTRE DIVISAS:
            //        Valor en pesos de LA UNIDAD de la divisa1 a convertir:
            var valor_obtenido: Double = businessIndexA.serie[0].valor

//        Valor total del monto buscado de la divisa 1 en pesos:
            var valor_total_div1_pesos: Double = valor_obtenido * monto.toDouble()

//        Valor de la Unidad de la Divisa 2 en pesos por BD local: valor_obtenido2_pesos
            var valor_obtenido2: Double = businessIndexB.serie[0].valor

//        Valor del monto total buscado en la divisa 1, en terminos de la divisa 2 =
//        valor_total_div1_dolares / valor_obtenido2_dolares

            var valor_total_div1_div2: Double = (valor_total_div1_pesos / valor_obtenido2)
//            valor_total_div1_div2 = String.format("%.2f", valor_total_div1_div2).toDouble()

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
                    //Nombre de la divisa solicitada
                    Text(
                        text = businessIndexA.nombre,
                        modifier = Modifier.padding(16.dp)
                    )
                    //Valor numerico en Double, en pesos obtenido de la API de la divisa 1 a convertir
//                var valor_obtenido: Double = businessIndex.serie[0].valor
                    Text(
                        text = String.format("%,.2f", valor_obtenido),
                        modifier = Modifier.padding(16.dp)
                    )
                    //Unidad de medida de la divisa solicitada: Pesos
                    Text(
                        text = businessIndexA.unidad_medida,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Row {
                    //        TEXTFIELD QUE MUESTRA EL VALOR TOTAL DE LA DIVISA 1 EN TERMINOS DE LA DIV2:
                    var ValFinalDolaresDiv2 by remember { mutableStateOf("") }
                    ValFinalDolaresDiv2 = valor_total_div1_div2.toString()
                    TextField(
                        value = ValFinalDolaresDiv2,
                        onValueChange = { ValFinalDolaresDiv2 = it },
                        label = { Text("Valor total en terminos de la Div 2") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }


//            Si no se encuentra la info del indicador, mostramos un mensaje de error:
        } else if (businessIndexA.codigo != "" || businessIndexB.codigo != "") {
            Text(
                text = "Ha ocurrido un error al obtener los datos del servicio. " +
                        "Pruebe ingresando una fecha diferente o intente más tarde.",
                modifier = Modifier.padding(16.dp)
            )
        }


//        Boton para mostrtar la lisa de indicadores obtenidas desde la API:
         Button(
             onClick = {
                 navController.navigate("indicador_Api_list")
                 //TOAST: Mensaje corto indep de la activity, que no interactua con el usuario
                 val text = "Obteniendo indicadores desde la API"
                 val duration: Int = Toast.LENGTH_LONG
                 Toast.makeText(navController.context, text, duration).show()
             },
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp)
         ) {
             Text("Listado de indicadores de API")
         }


    } //Fin columna
}
//Fin codigo IndexForm()