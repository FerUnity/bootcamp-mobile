package com.example.shoppinglistmodular.ui.screens.shoppingListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
//import cl.uchile.postgrado.mobile.shoppinglist.R
import com.example.shoppinglistmodular.R
import kotlinx.coroutines.CoroutineScope

// Componente que muestra la barra de navegación
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListTopBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Mi Lista de Compras",
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.Black
        )
    )
}

// Componente que muestra el botón flotante de la pantalla principal
@Composable
fun AddProductFloatingActionButton(navController: NavHostController) {
    ExtendedFloatingActionButton(
        containerColor = Color.Blue,
        contentColor = Color.White,
        onClick = { navController.navigate("add_product") }
    ) {
        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = "Agregar",
            tint = Color.Yellow
        )
        Text(
            text = "Agregar",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

// Componente que muestra la lista de productos
@Composable
fun ShoppingListForm(modifier: Modifier = Modifier,
                     navController: NavHostController,
                     productListModel: ShoppingListViewModel = viewModel()) {

    var seleccionado by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "Fondo de Pantalla",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    Column() {
        Spacer( modifier = Modifier.weight(1f) )
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Android de Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        items(productListModel.products) {producto ->
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                shape = CutCornerShape(8.dp),
                border = CardDefaults.outlinedCardBorder(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.5f)     // Transparenta el fondo
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = seleccionado,
                        onCheckedChange = { seleccionado = it } /* ,
                        modifier = Modifier.padding(end = 8.dp)*/
                    )
                    Text(
                        producto.productName,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {
                            navController.navigate("product_detail/" + producto.id)
                        },
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Blue
                        ),
                        shape = ButtonDefaults.elevatedShape
                    ) {
                        Text("Detalles")
                    }
                }
            }
        }
    }
}

// Componente que muestra la pantalla principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ShoppingListTopBar(drawerState, scope)
        },
        floatingActionButton = {
            AddProductFloatingActionButton(navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ShoppingListForm(
            modifier = Modifier.padding(innerPadding),
            navController
        )
    }
}