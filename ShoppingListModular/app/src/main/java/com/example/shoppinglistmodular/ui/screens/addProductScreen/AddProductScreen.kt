package com.example.shoppinglistmodular.ui.screens.addProductScreen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.shoppinglistmodular.ui.screens.shoppingListScreen.ShoppingListTopBar
import kotlinx.coroutines.CoroutineScope

// Componente que muestra la pantalla del detalle del producto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ShoppingListTopBar(drawerState, scope)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AddProductForm(
            modifier = Modifier.padding(innerPadding),
            navController
        )
    }
}