package com.example.proyectopersonal.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Sistema de Orientación Hospitalaria.",
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

//BOTTOMBAR:
// @Composable
//fun BottomBar() {
//    NavigationBar(
//        containerColor = Color.Blue,
//        contentColor = Color.White,
//        //Se define la elevacion por sobre el area de contenido:
//        tonalElevation = 10.dp,
//        windowInsets = BottomAppBarDefaults.windowInsets
//    ) {
//        //La sgte fila(Row) engloba los 3 elementos del bottomBar:
//        //Cada uno de esos 3 elementos esta contenido en una Columna
//        // que ontiene un Icon y un Text,
//        // y en que cada columna tiene el mismo peso = 1f:
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        )
//        {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Home,
//                    contentDescription = "Inicio",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "Inicio",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Email,
//                    contentDescription = "Correo",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "Correo",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.AccountCircle,
//                    contentDescription = "Cuenta",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//                Text(
//                    text = "Cuenta",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//        }
//        //Cierre Row que engloba horizontalmente las 3 cols:Inicio, Correo , Cuenta
//    } //Cierre cuerpo NavigationBar()
//}//Cierre fun BottomBar(){}
