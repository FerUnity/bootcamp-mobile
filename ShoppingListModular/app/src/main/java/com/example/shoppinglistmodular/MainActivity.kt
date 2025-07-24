package com.example.shoppinglistmodular

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import cl.uchile.postgrado.mobile.shoppinglist.ui.screens.addProductScreen.AddProductScreen
//import cl.uchile.postgrado.mobile.shoppinglist.ui.screens.productDetailScreen.ProductDetailScreen
//import cl.uchile.postgrado.mobile.shoppinglist.ui.screens.shoppingListScreen.ShoppingListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}

//AppNavigation es la funcion principal que permite navegar entre pantallas.
// Llama a la pantalla principal y secundaria(s)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") { AppFrame(navController) }
        composable("product_detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AppDetail(id, navController)
        }
//        composable("add_product") { backStackEntry ->
//            AddProductScreen(navController)
//        }
    }
}
@Composable
fun AppDetail(id: String?, navController: NavController){
    Column() {
        Text("Detalle del producto")
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ){
                //Cuerpo btn
            Text("Volver")
            }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Mi Lista de Compras",
                textDecoration = TextDecoration.Underline
            )

        },
        navigationIcon = {
            Icon(
                //imageVector = Icons.Filled.ArrowBack,
                //tint = Color.White,

                //Icono de Menu hamburguesa
                imageVector = Icons.Filled.Menu,
                //Color del menu hamburguesa
                tint = Color.White,
                //Contenido de texto paa ciegos:
                contentDescription = "Inicio",
                //Abrir el ModalNavigationDrawer
                modifier = Modifier
                    //El clickea el icon de menu hamb se abre el menu lateral drawer
                    .clickable { scope.launch { drawerState.open() } }

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
fun NavigationMenu(drawerState: DrawerState, scope: CoroutineScope) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = "Menú",
            modifier = Modifier.padding(bottom = 12.dp)
                .clickable{scope.launch { drawerState.close() }}
        )
        HorizontalDivider()

        //Con el sgte btn puedo cerrar el ModalNavigationDrawer, ver modifier:
        Text(
            text = "Opción 1",
            //cerrar el ModalNavigationDrawer
            modifier = Modifier.clickable {/*TO DO()*/ }
        )

        //Este btn no hace nada
        Text(
            text = "Opción 2",
            modifier = Modifier.clickable { /*TO DO()*/ }
        )

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
fun AppFrame(navController: NavHostController) {
    //Los 2 sgtes val se usan con el ModalNavDrawer(){}
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //Cuando se quiere confeccionar una barra laterial (ocultable) de navegación,
    // se utiliza el Drawer: ModalNavigationDrawer.    //•
    //A diferencia de las secciones anteriores, esta barra contiene al Scaffold
    // aunque el Saffold puede estar sin el ModalNavigationDrawer(),
    // debiendo de poner la estructura como contenido de la navegación.
    //De esta manera, el ModalNavigationDrawer permite
    // incorpora opciones propias de navegación como perfil de usuario,
    // secciones de la app, entre otras funciones.
    ModalNavigationDrawer(
        drawerState = drawerState,

        //En drawerContent van los elementos que del menu de este widget,
        // que en este caso es una columna con 3 textos:
        drawerContent = {

            //Fun de los textos del ModalNavigationDrawer
            NavigationMenu(drawerState, scope)



        }
    )

    //Abre content o cuerpo de ModalNavigationDrawer()
    {

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


            },
            bottomBar = {
                BottomBar()

            },

            //Boton flotante redondo, rojo con el singo +:
            floatingActionButton = {
                FAButton()
            },
            modifier = Modifier.fillMaxSize()
        )
        //Fin atributos Scaffold

        //Llamamos a la fun composable ShoppingList(), de abajo,
        // en el content del Scaffold, o sea entre sus {}
        { innerPadding ->
            ShoppingList(
                modifier = Modifier.padding(innerPadding)
            )
        }//Cierre content Scaffold

    } //Cierre ModalNavDrawer
}
//Cierre AppFrame

@Composable
fun ShoppingList(modifier: Modifier = Modifier) {
    var seleccionado by remember { mutableStateOf(false) }
    //El poblema que como existe una sola va seleccionado,
    // se selecionan todos los productos

    //La lista de elem se llama items
    val productos = listOf("Leche", "Huevos", "Spaghetti", "Arroz", "Comida para perros Dog Chau")
    //Una imagen de fondo detras debajo el LazyColumn:
    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "Fondo de pantalla",
        contentScale = ContentScale.Crop,
        //Se tiene definir el tamaño asi:
        modifier = Modifier
            .fillMaxSize()
    )

    //Creamos una colmn para poner la imagen mas abajo y
    // dividimos el total del espacio entre un espacio vacio o Spacer y la Image:
    Column() {
        //Ambos elementos, el Spacer y la Image, tienen igual weight = 1f,
        // por ende ocupan el 50% del espacio cada uno:
        //El Spacer es un espacio vacio:
        Spacer(modifier = Modifier.weight(1f))
        Image(
            //painter = painterResource(R.drawable.mar),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            //Se tiene definir el tamaño asi:
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)

        )


    }
    //Cierre Column para Spacer e imagen de Android

    //Lazy Column estoda la lista de productos:
    // en que cada producto es un Card que contiene una fila(Row) con estos 3 elementos:
    // checkbox, producto y btn Detalles
    LazyColumn(
        modifier = Modifier
            //Para que el LazyColumn ocupe el total del espacio disponible
            .fillMaxSize()
            .padding(top = 80.dp, bottom = 80.dp)
    ) {
        //La fun se llama items(Y su param es la lista productos, son cosas distintas,
        // ademas producto es cada elemento de la lista productos)
        items(productos) { producto ->
            //Que por cada producto de la lista productos haga una fila Row:
            // con un checkbox,un texto y un boton detalles.
            // Y ademas que cada fila de poductos quede
            // encerrada en un Card:
            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                shape = CutCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    //Que quede semi-transparente la image
                    //containerColor = Color.White.copy(alpha = 0.5f)
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            )
            { //Cuerpo Card:
                //Creamos una fila o Row que organizara horizontalmente,
                // los 3 elementos de:
                //Checkbox, nombre poducto y btn Detalles
                Row(
                    Modifier
                        //Cada elem usa todo el ancho disponible
                        .fillMaxWidth()

                        //Separacion entre cada elemento:
                        .padding(8.dp),

                    //verticalAlignment Indica hacia se van a ir orsenando los elem de la lista
                    verticalAlignment = Alignment.CenterVertically

                    //horizontalAlignment, organiza el elem horizontalmente
                ) {

                    //El poblema que como existe una sola va seleccionado,
                    // se selecionan todos los productos
                    //Switch
                    Checkbox(
                        checked = seleccionado,
                        onCheckedChange = { seleccionado = it },
                        //El enable habilita o no el Checkbox, por def es true
                        // enabled = false

                    )

                    Text(
                        producto,
                        //Que el texto ocupe el total del espacio horozontal disponible,
                        // antes del espacio del btn detalles:
                        modifier = Modifier.weight(1f)
                    )


                    //Y erl btn Detaklles para cada producto de Productos:
                    Button(
                        onClick = {/*TODO*/ },
                        modifier = Modifier.padding(start = 8.dp),
                    ) {
                        Text("Detalles")
                    }
                } //Cierre Row


            } //Cierre Card que contiene cada fila (Row) de cada poducto
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    AppNavigation()
}
