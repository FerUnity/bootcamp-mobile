package com.example.proyectopersonal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.proyectopersonal.MainActivity
import com.example.proyectopersonal.R
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexTopBar(drawerState: DrawerState, scope: CoroutineScope, text: String) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val userSettingsViewModel = MainActivity.userSettingsViewModel
    var selectedTheme by remember { mutableStateOf(userSettingsViewModel.theme) }
    CenterAlignedTopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge

            )

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = {
                /* Open Account Modal */
            }) {
                Icon(
                    //imageVector = Icons.Filled.ArrowBack,
                    //tint = Color(white),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Inicio",
                    //Abrir el ModalNavigationDrawer
                    //modifier = Modifier.clickable { scope.launch { drawerState.open() } }

                )
            }


        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Localized description"
                )
            }
            ThemeSettingDialog(
                showDialog = showDialog,
                title = "Theme Settings",
                onDismiss = { showDialog = false },
                currentTheme = selectedTheme,
                onThemeChange = { theme ->
                    selectedTheme = theme
                    userSettingsViewModel.theme = theme
                    userSettingsViewModel.saveThemeSetting(MainActivity())
                    showDialog = false
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Theme Settings") },
                    onClick = { showDialog = true },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Language Settings") },
                    onClick = {  },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_language_24),
                            contentDescription = null
                        )
                    }
                )
            }
        },
        scrollBehavior = scrollBehavior




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
