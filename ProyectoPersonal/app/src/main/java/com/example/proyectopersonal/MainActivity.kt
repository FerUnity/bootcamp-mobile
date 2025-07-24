package com.example.proyectopersonal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.proyectopersonal.ui.theme.ProyectoPersonalTheme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoPersonalTheme {
                MiProyecto()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Sistema de Consulta Hospitalaria",
                textDecoration = TextDecoration.None,
                fontSize = 25.sp

            )

        },
        navigationIcon = {
            Icon(
                //imageVector = Icons.Filled.ArrowBack,
                //tint = Color(white),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Inicio",
                //Abrir el ModalNavigationDrawer
                //modifier = Modifier.clickable { scope.launch { drawerState.open() } }

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.Black
        )

    )

}

@Composable
fun FAButton(){
    //ExtendedFloatingActionButton o LargeFloatingActionButton:
    ExtendedFloatingActionButton(
        containerColor = Color.Blue,
        contentColor = Color.White,
        onClick = { /*TODO*/ }
    ) {
        //Cuerpo boton
        Icon(
            //Icono +:
            imageVector = Icons.Filled.Add,
            contentDescription = "Agregar",
            //Pinto amarillo el color del icono +:
            tint = Color.Yellow,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text("Agregar")


    }
}

@Composable
fun BottomBar(){
    NavigationBar(
        containerColor = Color.Blue,
        contentColor = Color.White,
        //Se define la elevacion por sobre el area de contenido:
        tonalElevation = 10.dp,
        windowInsets = BottomAppBarDefaults.windowInsets
    ) {
        //La sgte fila(Row) engloba los 3 elementos del bottomBar:
        //Cada uno de esos 3 elementos esta contenido en una Columna
        // que ontiene un Icon y un Text,
        // y en que cada columna tiene el mismo peso = 1f:
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Inicio",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Correo",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Correo",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Cuenta",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Cuenta",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        //Cierre Row que engloba horizontalmente las 3 cols:Inicio, Correo , Cuenta
    } //Cierre cuerpo NavigationBar()
}//Cierre fun BottomBar(){}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiProyecto(
//    name: String,
//    modifier: Modifier = Modifier
)
{
    // Declaramos las 2 estructuras de datos a utilizar en la Screen
    //Los 2 sgtes val se usan con el ModalNavDrawer(){}
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    //Para el menu desplegable:
    val hospitalCategories: List<String> = listOf("Mutual","San Borja","JJAguirre")
    var expanded by remember { mutableStateOf(false) }
    var hospitalCategory by remember { mutableStateOf(hospitalCategories[0]) }

    //Scaffold es un componente que permite construir pantallas organizadas,
    // incorporando áreas predefinidas como:
    //topBar: barra superior
    //bottomBar: barra inferior
    //floatingActionButton(FAB): botón flotante
    //content: zona principal de la pantalla
    //Es ideal para crear pantallas coherentes con las guías de Material Design.

    Scaffold(
        topBar = {

            TopBar(drawerState, scope)


        }
//        bottomBar = {
//            BottomBar()
//
//        },

        //Boton flotante redondo, rojo con el singo +:
//        floatingActionButton = {
//            FAButton()
//        },
//        modifier = Modifier.fillMaxWidth()
    )
    //Fin atributos Scaffold

    //Llamamos a la fun composable ShoppingList(), de abajo,
    // en el content del Scaffold, o sea entre sus {}
    {  //Cuerpo Scaffold:

            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
            //verticalArrangement = Arrangement.spacedBy(16.dp),
        ){

        } //Cierre Column del innerPadding

    }//Cierre content Scaffold


    // Definimos la estructura general de la aplicación en formato vertical
    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()
            //Para evitar que el teclado tape los componenetes en la pantalla:
            .verticalScroll(scrollState)
            .imePadding(),


    ) {

        //Agregamos un menu desplegable de Hpsitales:
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = hospitalCategory,
                onValueChange = { },
                readOnly = true, //No pemite escribir en el textField
                label = { Text("Seleccione un Hospital") },
                //Icono triangulo chico para desplegar el menu
                trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    //Esencial para que funcione correctamente el menu desplegable:
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)

            )
            ExposedDropdownMenu(
                expanded = expanded,
                //Para que al presionar en cualq parte se cierre el menu desplegable
                onDismissRequest = { expanded = false }
            ) {
                hospitalCategories.forEach { opt ->
                    //Por cada item del menu desplegable, se crea un item:
                    DropdownMenuItem(
                        //Como texto va cada categ de la lista de hospitales:
                        text = { Text(opt) },
                        onClick = {
                            //Al hacer click asigno la opcion que elegi
                            // a la var hospitalCategoy
                            // y se muestra en el texto del TextField:
                            hospitalCategory = opt
                            //Luego cierro el menu desplegable:
                            expanded = false
                        }
                    ) //Cierre DropdownMenuItem
                }
            }


        }
    } //Cierre Column



}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPersonalTheme {
        MiProyecto()
    }
}