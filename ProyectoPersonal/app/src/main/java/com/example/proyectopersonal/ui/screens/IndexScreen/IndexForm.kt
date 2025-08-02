package com.example.proyectopersonal.ui.screens.IndexScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexForm(
        navController: NavHostController,
        innerPadding: PaddingValues,
        indexModel: IndexViewModel = viewModel()
    //Creamos var indexModel de tipo IndexViewModel y el viewModel() es para que sea persistente
) {
    // Declaramos las 2 estructuras de datos a utilizar en la Screen
    //Los 2 sgtes val se usan con el ModalNavDrawer(){}
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Definimos la estructura general de la aplicación en formato vertical:
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            //Para evitar que el teclado tape los componenetes en la pantalla:
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        //En el cont de la Column armamos el formulario:
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .background(Color.Cyan)


        ) {

            Text(
                "Por favor seleccione un Hospital",
                Modifier.padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.None,
                fontSize = 25.sp

            )

        }


        //Lista de indices disponibles esto es del negocio no de la pantalla, por ende va al viewModel::
        //val indexOptions: List<String> = listOf("UF", "UTM", "UTA", "Dolar", "Euro")
        //Var que recorre la lista de indices, tb es del negocio por ende tb va al viewModel:
        //var selectedIndex: String by remember { mutableStateOf(indexOptions[0]) }

        //PARA ELEGIR: HOSPITALES O ESPECIALIDADES:
        // Creamos una priemra lista desplegable, la var exanded es de la pantalla no del negocio:
        var expandedIndexType: Boolean by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedIndexType,
            //Luego para que cambie de estado de abierto a cerrado,
            // el menu desplegable con onExpandedChange:
            onExpandedChange = { expandedIndexType = !expandedIndexType },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            //Cont del ExposedDropdownMenuBox:
            TextField(
                value = " ",
                onValueChange = {},
                //No se puede escribir, solo aparece la opcion elegida:
                readOnly = true,
                label = { Text("Tipo de Servicio") },
                //Icono triangulo chico para desplegar el menu:
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIndexType) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                // Importante para que funcione correctamente
            )

            ExposedDropdownMenu(
                expanded = expandedIndexType,
                //Si pincho en cualq parte de la antalla que se cierre el menu desplegable:
                onDismissRequest = { expandedIndexType = false }
            ) {
                //indexOptions es la lista de indices disponibles,
                // y por cada opcion de la lista hacemos un DropdownMenuItem:
                indexModel.indexTypeOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            expandedIndexType = false
                            //En la var  indexModel.onIndexChange(option), guardamos la opcion elegida:
                            indexModel.onIndexTypeChange(option)

                        }
                    )
                }
            }


        } //Cierre ExposedDropdownMenuBox

        //PARA EL SERVICIO ELEGIDO: SEGUN SE SELECCIONO ANTEIORMENTE HOSPITALES O ESPECIALIDADES:
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 24.dp)
                .background(Color.Cyan)

        ) {

            Text(
                "Por favor seleccione una opcion",
                Modifier.padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.None,
                fontSize = 25.sp
            )

        }


        //UNA VEZ ELEGIDO HOSPITALES O ESPECIALIDADES:
        // Creamos una segunda lista desplegable, la var exanded es de la pantalla no del negocio:
        var expandedIndex: Boolean by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedIndex,
            //Luego para que cambie de estado de abierto a cerrado,
            // el menu desplegable con onExpandedChange:
            onExpandedChange = { expandedIndex = !expandedIndex },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            //Cont del ExposedDropdownMenuBox:
            TextField(
                value = " ",
                onValueChange = {},
                //No se puede escribir, solo aparece la opcion elegida:
                readOnly = true,
                label = { Text("Seleccion") },
                //Icono triangulo chico para desplegar el menu:
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIndex) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                // Importante para que funcione correctamente
            )

            ExposedDropdownMenu(
                expanded = expandedIndex,
                //Si pincho en cualq parte de la antalla que se cierre el menu desplegable:
                onDismissRequest = { expandedIndex = false }
            ) {
                //indexOptions es la lista de indices disponibles,
                // y por cada opcion de la lista hacemos un DropdownMenuItem:
                indexModel.getIndexOptions().forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            expandedIndex = false
                            //En la var  indexModel.onIndexChange(option), guardamos la opcion elegida:
                            indexModel.onIndexChange(option)

                        }
                    )
                }
            }


        } //Cierre ExposedDropdownMenuBox

        //ERROR DE SELECCION VACIA:
        // Creamos un mensaje emergente de error con la fun let y las var de error del IndexViewModel:
        indexModel.indexErrorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        //PARA LA FECHA
        //Para ingresar la fecha ponemos un TextField, date tb es el del negocio por ende va al viewModel:
//            var date by remember { mutableStateOf("") }
//        TextField(
//            value = indexModel.date,
//            onValueChange = { indexModel.onDateChange(it) },
//            placeholder = { Text("Formato dd/mm/aaaa") },
//            label = { Text("Fecha") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )

        //ERROR DE FECHA O DATE:
        // Creamos un mensaje emergente de error con la fun let y las var de error del IndexViewModel:
//            indexModel.indexErrorMessage?.let { errorMessage ->
//                Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)
//            }
//        indexModel.dateErrorMessage?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }


        //Boton para que una vez seleccla opcion elegida, lo ingrese y nos lleve a la pantalla
        //de consulta del detalle de la opcion seleccionado:
        Button(
            onClick = {
                //USamos la fun validateForm() del archivo IndexViewModel.kt para vakidar el llenado del form,
                // guardamos el resultado de la validacion en un valor:
                val result = indexModel.validateForm()
                if (result.isSuccess) {
                    //Nos vamos a la sgte pantalla, asi:
                    //Fijarse que se pasan los 2 param del formulario asi,
                    // ${indexModel.index } y ${indexModel.date}"), asi:
                    navController.navigate("index_detail/${indexModel.index }")}
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Consultar")

        }

    }   //Cierre cont Column()

} //Cierre fun IndexForm()








//MI COLUMNA PROY PERSONAL ORIGINAL:
//    Column(
//        modifier = Modifier
//            .padding(top = 100.dp)
//            .fillMaxSize()
//            //Para evitar que el teclado tape los componenetes en la pantalla:
//            .verticalScroll(scrollState)
//            .imePadding(),
//
//
//        ) {
//        //Content columna
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 12.dp, end = 12.dp)
//                .background(Color.Cyan)
//
//
//        ) {
//
//            Text(
//                "Por favor seleccione un Hospital",
//                Modifier.padding(top = 10.dp, bottom = 10.dp),
//                textAlign = TextAlign.Center,
//                textDecoration = TextDecoration.None,
//                fontSize = 25.sp
//
//            )
//
//        }
//
//
//        //Agregamos un menu desplegable de Hositales:
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = { expanded = !expanded },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            TextField(
//                value = hospitalCategory,
//                onValueChange = { },
//                readOnly = true, //No pemite escribir en el textField
//                label = { Text("Seleccione un Hospital") },
//                //Icono triangulo chico para desplegar el menu
//                trailingIcon = { TrailingIcon(expanded = expanded) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    //Esencial para que funcione correctamente el menu desplegable:
//                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
//
//            )
//            ExposedDropdownMenu(
//                expanded = expanded,
//                //Para que al presionar en cualq parte se cierre el menu desplegable
//                onDismissRequest = { expanded = false }
//            ) {
//                hospitalCategories.forEach { opt ->
//                    //Por cada item del menu desplegable, se crea un item:
//                    DropdownMenuItem(
//                        //Como texto va cada categ de la lista de hospitales:
//                        text = { Text(opt) },
//                        onClick = {
//                            //Al hacer click asigno la opcion que elegi
//                            // a la var hospitalCategoy
//                            // y se muestra en el texto del TextField:
//                            hospitalCategory = opt
//                            //Luego cierro el menu desplegable:
//                            expanded = false
//                        }
//                    ) //Cierre DropdownMenuItem
//                }
//            }
//
//
//        } //Cierre content ExposedDropdownMenuBox_1
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 12.dp, end = 12.dp, top = 24.dp)
//                .background(Color.Cyan)
//
//        ) {
//
//            Text(
//                "Por favor seleccione una Especialidad",
//                Modifier.padding(top = 10.dp, bottom = 10.dp),
//                textAlign = TextAlign.Center,
//                textDecoration = TextDecoration.None,
//                fontSize = 25.sp
//            )
//
//        }
//
//
//        //Cremos un segundo ExposedDropdownMenuBox para especialidades:
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = { expanded = !expanded },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            TextField(
//                value = especialidadCategory,
//                onValueChange = { },
//                readOnly = true, //No pemite escribir en el textField
//                label = { Text("Seleccione la Especialidad") },
//                //Icono triangulo chico para desplegar el menu
//                trailingIcon = { TrailingIcon(expanded = expanded) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    //Esencial para que funcione correctamente el menu desplegable:
//                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
//
//            )
//            ExposedDropdownMenu(
//                expanded = expanded,
//                //Para que al presionar en cualq parte se cierre el menu desplegable
//                onDismissRequest = { expanded = false }
//            ) {
//                especialidadCategories.forEach { opt ->
//                    //Por cada item del menu desplegable, se crea un item:
//                    DropdownMenuItem(
//                        //Como texto va cada categ de la lista de hospitales:
//                        text = { Text(opt) },
//                        onClick = {
//                            //Al hacer click asigno la opcion que elegi
//                            // a la var hospitalCategoy
//                            // y se muestra en el texto del TextField:
//                            especialidadCategory = opt
//                            //Luego cierro el menu desplegable:
//                            expanded = false
//                        }
//                    ) //Cierre DropdownMenuItem
//                }
//            }
//
//
//        } //Cierre content ExposedDropdownMenuBox_2
//
//
//
//
//    } //Cierre Column




