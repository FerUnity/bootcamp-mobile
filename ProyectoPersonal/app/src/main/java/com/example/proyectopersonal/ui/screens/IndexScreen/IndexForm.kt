package com.example.proyectopersonal.ui.screens.IndexScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.proyectopersonal.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexForm(
        navController: NavHostController,
        innerPadding: PaddingValues,
        snackbarHostState: SnackbarHostState,
        scope: CoroutineScope,
        indexModel: IndexViewModel = viewModel()
    //Creamos var indexModel de tipo IndexViewModel y el viewModel() es para que sea persistente
) {
    // Declaramos las 2 estructuras de datos a utilizar en la Screen
    //Los 2 sgtes val se usan con el ModalNavDrawer(){}
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    //Otra imagen de fondo de pantalla:
    Image(
        painter = painterResource(R.mipmap.ic_bg_hospitales2_foreground),
        contentDescription = "Fondo de pantalla",
        contentScale = ContentScale.Fit,
        //Se tiene definir el tamaño asi:
        modifier = Modifier
            .fillMaxSize()
    )

    //Una imagen de fondo de pantalla:
    //Generar un espacio o Spacer para que el logo de Hospital
    // pueda ocupar la parte inf de la pantalla,
    // Para usar weight debe estar dentro de un Column():
    Column() {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.mipmap.ic_bg_hospitales_foreground),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Fit,
            //Se tiene definir el tamaño asi:
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }

    // Definimos la estructura general de la aplicación en formato vertical:
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            //Para evitar que el teclado tape los componenetes en la pantalla:
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        //En el cont de la Column armamos el formulario:
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 12.dp, end = 12.dp)
                .background(Color.Green)


        ) {

            Text(
                //Por favor seleccione un hospital o una especialidad
                stringResource(R.string.deployment_1),
                Modifier.padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.None,
                style = MaterialTheme.typography.titleLarge,
                //fontSize = 25.sp


            )

        }


        //Lista de indices disponibles esto es del negocio no de la pantalla, por ende va al viewModel::
        //val indexOptions: List<String> = listOf("UF", "UTM", "UTA", "Dolar", "Euro")
        //Var que recorre la lista de indices, tb es del negocio por ende tb va al viewModel:
        //var selectedIndex: String by remember { mutableStateOf(indexOptions[0]) }

        //PARA ELEGIR: HOSPITALES O ESPECIALIDADES:
        // Creamos una priemra lista desplegable, la var expanded es de la pantalla no del negocio:
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
                value = indexModel.indexType,
                onValueChange = {},
                //No se puede escribir, solo aparece la opcion elegida:
                readOnly = true,
                label = { Text(stringResource(R.string.select_service)) },
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


        } //Cierre ExposedDropdownMenuBox 1

        //PARA EL SERVICIO ELEGIDO: SEGUN SE SELECCIONO ANTEIORMENTE HOSPITALES O ESPECIALIDADES:
        Box(
            //Caja que contiene un texto:
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 24.dp)
                .background(Color.Green)

        ) {

            //TExto: Por favor seleccione una opcion
            Text(
                stringResource(R.string.option_select),
                Modifier.padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.None,
                style = MaterialTheme.typography.titleLarge
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
                value = indexModel.index,
                onValueChange = {},
                //No se puede escribir, solo aparece la opcion elegida:
                readOnly = true,
                label = { Text(stringResource(R.string.selection)) },
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


        } //Cierre ExposedDropdownMenuBox 2

        //ERROR DE SELECCION VACIA:
        // Creamos un mensaje emergente de error con la fun let y las var de error del IndexViewModel:
        // :"Por favor seleccione una opcion"
        indexModel.indexErrorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        //Boton para que una vez seleccla opcion elegida, lo ingrese y nos lleve a la pantalla
        //de consulta del detalle de la opcion seleccionado: indexDetailScreen.kt
        Button(
            onClick = {
                //USamos la fun validateForm() del archivo IndexViewModel.kt para validar el llenado del form,
                // guardamos el resultado de la validacion en un valor:
                val result = indexModel.validateForm()
                if (result.isSuccess) {
                    //OJO: Con la sgte indicacion:
                    // navController.navigate("index_detail/${indexModel.index }"),
                    // le decimos a este btn onClick,
                    // que nos lleve a la pantalla secundaria IndexDetailScreen.kt:
                    //Fijarse que se pasan el param del formulario asi,
                    // ${indexModel.index }, asi:
                    navController.navigate("index_detail/${indexModel.index }")

                    //TOAST: Mensaje corto indep de la activity, que no interactua con el usuario
                    val text = "Detalle de la opcion"
                    val duration: Int = Toast.LENGTH_LONG
                    Toast.makeText(navController.context, text, duration).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                // en Color.kt y al tema en Theme.kt:
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ),
        ) {
            //TExto sobre el btn: "Consultar"
            Text(stringResource(R.string.details_button))

        }


//        ACA PODEMOS CREAR UN BTN PARA ACCEDER AL FORMULARIO PARA SOLICITAR MEDICAMENTOS:
        Button(
            onClick = {
                //OJO: Con la sgte indicacion:
                // navController.navigate("index_detail/${indexModel.index }"),
                // le decimos a este btn onClick,
                // que nos lleve a la pantalla secundaria IndexDetailScreen.kt:
                //Fijarse que se pasan el param del formulario asi,
                // ${indexModel.index }, asi:
                navController.navigate("add_medicamento")

                //TOAST: Mensaje corto indep de la activity, que no interactua con el usuario
                val text = "Detalle de la opcion"
                val duration: Int = Toast.LENGTH_LONG
                Toast.makeText(navController.context, text, duration).show()

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                // en Color.kt y al tema en Theme.kt:
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ),
        ) {
            //TExto sobre el btn: "Consultar"
            Text(stringResource(R.string.form_meds))

        }



    }   //Cierre cont Column()

} //Cierre fun IndexForm()



