package com.example.indicadoresmvp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator

//ACA EN EL BOOTONBAR DEFINIMOS SI LOS INDICES SON NAC O INT:
// Creamos una clase enum, llamada Destination para crear las 2 OBJETOS de esa clase:
//NAC, INT:
enum class Destination(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String,
    val label: String
) {
    NAC("nacional", icon = Icons.Filled.Favorite, contentDescription = "Nacionales", label = "Nacional"),
    INT("internacional", icon = Icons.Filled.FavoriteBorder, contentDescription = "Internacionales", label = "Internacional")
}
//Cierre de la clase Enum
@Composable
fun SectionBottomBar(navController: NavHostController, actualDestination: Destination){
//    Para guardar en una var Int la pos del Item seleccionado: Para guardar en una var NAC y en la otra var la pos para INT:
    var selectedDestination by rememberSaveable { mutableIntStateOf(actualDestination.ordinal) }
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets){
//        Usamos el enum class Destination() para recorrerlo y seleccionar una de las 2 opciones: NAC o INT,
        //        con sus 4 parametros: route, icon, contentDescription y label:
       Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedDestination == index,
                onClick = {
//                    NAvegamos al formul corresp. NAC O INT:
//                    Ruta: "nacional" o "internacional"
                    navController.navigate(destination.route)
                    selectedDestination = index
                },
//                Icono
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = destination.contentDescription
                    )
                },
                label = {
                    Text(destination.label)
                }
            )
        }
    }
}