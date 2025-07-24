package com.example.shoppinglistmodular.ui.screens.productDetailScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
//import cl.uchile.postgrado.mobile.shoppinglist.ui.screens.shoppingListScreen.ShoppingListTopBar
import com.example.shoppinglistmodular.ui.screens.shoppingListScreen.ShoppingListTopBar
import kotlinx.coroutines.CoroutineScope

// Componente que muestra el detalle del producto
@Composable
fun ProductDetailForm(id: String?, modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()
    ) {
        Text("Detalle del Producto")
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Volver")
        }
    }
}

// Componente que muestra la pantalla del detalle del producto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(id: String?, navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ShoppingListTopBar(drawerState, scope)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ProductDetailForm(
            id,
            modifier = Modifier.padding(innerPadding),
            navController
        )
    }
}