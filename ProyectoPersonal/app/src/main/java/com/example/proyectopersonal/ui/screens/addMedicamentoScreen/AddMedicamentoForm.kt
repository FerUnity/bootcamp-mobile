package com.example.proyectopersonal.ui.screens.addMedicamentoScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.components.PrimaryButton
import com.example.proyectopersonal.ui.components.SecondaryButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Componente que muestra el detalle del producto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicamentoForm(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    //Se crea una var con el nombre que queramos productVM o viewModel,
    // de clase AddProductViewModel, con param viewModel():
    //productVM: AddProductViewModel = viewModel()) {
    viewModel: AddMedicamentoViewModel = viewModel()
) {

    //Lista expandible:
    //La sgte lista tb deberia pasar al viewModel o al ProductData:
    val medicamentoCategories = listOf("Orales: comprimidos", "Tópicos: pomadas", "Ópticos: gotas para los ojos",
        "Intravenosos o intramusculares: viales", "Intradérmicos: insulina", "Rectales o vaginales: supositorios u óvulos")
    var expanded by remember { mutableStateOf(false) }

    //Para activar las animaciones:
    var visible: Boolean by remember { mutableStateOf(false) }
    // OJO: Para las animaciones de error de todos los campos:
    var productNameErrorVisible: Boolean by remember { mutableStateOf(false) }
    var productBrandErrorVisible: Boolean by remember { mutableStateOf(false) }
    //var productDescriptionErrorVisible: Boolean by remember { mutableStateOf(false) }
    //var productCategoryErrorVisible: Boolean by remember { mutableStateOf(false) }
    var productPriceErrorvisible: Boolean by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    //Imagen de fondo
    Image(
        painter = painterResource(R.mipmap.ic_bg_hospitales2_foreground),
        contentDescription = "Fondo de pantalla",
        contentScale = ContentScale.Fit,
        //Se tiene definir el tamaño asi:
        modifier = Modifier
            .fillMaxSize()
    )

    //Generar un espacio o Spacer para que el logo de android ocupe la parte inf de la pantalla:
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

    //Columna sonde estan lños widgets del formulario:
    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()
            //Para desplazar la pantalla hacia arriba cuando se llama al teclado
            // y este no tape los campos, hacemos:
            .verticalScroll(scrollState)
            .imePadding(),
        //Ojo el FAB Si queda tapado
    ) {
        Text(
            text = stringResource(R.string.add_button),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        TextField(
            //Delegamos a la clase AddMedicamentoViewModel los valores de value y onValueChange,
            // para que estos no se borran por ej al rotar la pantalla:
            value = viewModel.productName,
            //Y cada vez que se haga un cambio, que se valide el campo respectivo,
            // pero lo hacemos en el btn on click mejor:
            onValueChange = {
                viewModel.onProductNameChange(it)
                //viewModel.validateForm()
            },
            label = { Text("Producto") },
            placeholder = { Text("Nombre del Producto") },
            isError = viewModel.productNameError != null,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        //Y si me da un ERROR despues de escribir el nombre del prod:
        //OJO: Animacion para mostrar el error.
        // La animacion se activara cuando el valor del error(viewModel.productNameError) NO sea null:
        AnimatedVisibility(visible = viewModel.productNameError != null) {
            //Gramos el texto de error con la animacion:
            Text(
                text =  viewModel.productNameError?: "",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

        }

        TextField(
            //Delegamos a la clase AddProductViewModel los valores de value y onValueChange,
            // para que estos no se borran por ej al rotar la pantalla:
            value = viewModel.productBrand,
            onValueChange = {
                viewModel.onProductBrandChange(it)
            },
            label = { Text("Marca") },
            placeholder = { Text("Marca del Producto") },
            isError = viewModel.productBrandError != null,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        //Si tira error tb>:
        AnimatedVisibility(visible = viewModel.productBrandError != null) {
            //Gramos el texto de error con la animacion:
            Text(
                text =  viewModel.productBrandError?: "",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

        }

        TextField(
            //Delegamos a la clase AddProductViewModel los valores de value y onValueChange,
            // para que estos no se borran por ej al rotar la pantalla:
            value = viewModel.productDescription,
            onValueChange = { viewModel.onProductDescriptionChange(it) },
            label = { Text("Descripción") },
            placeholder = { Text("Descripción del Producto") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        //Creacion de lista desplegable categorias de medicamentos
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                //Delegamos a la clase AddProductViewModel los valores de value y onValueChange,
                // para que estos no se borran por ej al rotar la pantalla:
                value = viewModel.productCategory,
                onValueChange = { },
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                // Importante para que funcione correctamente

            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                medicamentoCategories.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt) },
                        onClick = {
                            //Delegamos a la clase viewModel: AddProductViewModel para que al hacer click,
                            // cambie el valor del desplegable por el texto elegido que esta en
                            // AddProductViewModel.onProductCategoryChange():
                            viewModel.onProductCategoryChange(opt)
                            expanded = false
                        }
                    )
                }
            }
        }
        //Fin lista desplegable

        TextField(
            //Delegamos a la clase AddProductViewModel los valores de value y onValueChange,
            // para que estos no se borran por ej al rotar la pantalla:
            value = viewModel.productPrice,
            onValueChange = {
                viewModel.onProductPriceChange(it)
            },
            label = { Text("Precio") },
            placeholder = { Text("Precio del Producto") },
            isError = viewModel.productPriceError != null,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        //Si tira error:
        AnimatedVisibility(visible = viewModel.productPriceError != null) {
            //Gramos el texto de error con la animacion:
            Text(
                text =  viewModel.productPriceError?: "",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

        }

        //En una fila agregamos los 2 botones luego de llenar este formulario:
        Row {
            //Botn secundario para Cancelar y volver a la pantalla principal:
            SecondaryButton(
                text = stringResource(R.string.cancel_button),
                onClick = { navController.popBackStack() }
            )

            // Btn primario para guardar el prod que se lleno en el formulario
            // y nos lleva a la pantalla principal ShoppingListScreen.kt:
            PrimaryButton(
                stringResource(R.string.save_button),
                onClick = {
                    viewModel.validateForm()
                    if (viewModel.isFormValid) {
                        //Despues de agregar el producto la idea es volver a la pantalla anterior
                        // recuperando el total del contenido que se acaba de agregar y enviar, asi:
                        viewModel.addProduct(navController.previousBackStackEntry?.savedStateHandle)

                        //Aqui va el toast o snackbar:

                        //OPT-1: TOAST: Mensaje corto indep de la activity, que no interactus con el usuario
                        val text: String = "Producto agregado a la lista"
                        val duration: Int = Toast.LENGTH_SHORT
                        Toast.makeText(navController.context, text, duration).show()

                        //OPT-2: SNACKBAR V1 corta: MEnsaje largo:
                        scope.launch {
                            snackbarHostState.showSnackbar("Producto agregado a la lista")
                        }
                        //Fin snackbar v1.

                        //SNACKBAR V2 larga: MEnsaje largo que permite interaccion con el usuario, una x,etc
                        // Sirve para arrepentirse del prod agregado o para editar el formulario del prod:
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                //Mensaje en el SnackBar:
                                message = "Esta seguro de agregar el producto?",

                                //Boton de activacion de la accion de agregar el prod, con el texto Si:
                                actionLabel = "Si",

                                //Boton X, para cancelar la accion de agregar el prod:
                                withDismissAction = true,

                                //La duracion del snackbar es indef hasta que se apriete algun boton:
                                duration = SnackbarDuration.Indefinite
                            )
                            when (result) {
                                //Luego de pres este boton "Si",
                                //hacemos un popBackStack() para que
                                // nos envia hacia la pantalla anterior: ShoppingListScreen.kt,
                                //que es la lista de productos con el btn detalles y el FAB,
                                SnackbarResult.ActionPerformed -> {
                                    navController.popBackStack()
                                    //Y muestro un mensaje o Toast:
                                    Toast.makeText(
                                        navController.context,
                                        "Producto agregado a la lista",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                //Si apreto el btn X para cancelar la accion de agregar un prod a la lista,
                                // que se muestre un Toast:
                                SnackbarResult.Dismissed -> {
                                    //No se cmabia de pantalla, solo se muestra un toast:
                                    Toast.makeText(
                                        navController.context,
                                        "Accion cancelada, Producto no agregado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        //Fin snackbar v2.


                        //Luego de pres este boton,
                        //hacemos un popBackStack() para que
                        // nos envia hacia la pantalla anterior: ShoppingListScreen.kt,
                        // que es la principal,
                        // y que es la lista de productos con el btn detalles y el FAB,
                        // que va a agregar un nuevo producto, segun que llenamos aca:
                        navController.popBackStack()

                    } //Cierre if()


                } //Cierre onClick()
            )
            //Boton "guardar" de la PrimaryButton.kt: Que agrega un prod a la lista


        }
    }
}