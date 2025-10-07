package com.example.proyectopersonal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle.Companion.dark
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectopersonal.ui.theme.ProyectoPersonalTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectopersonal.ui.screens.IndexDetailScreen.IndexDetailScreen
import com.example.proyectopersonal.ui.screens.IndexScreen.IndexScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.proyectopersonal.model.UserSettingsViewModel
import com.example.proyectopersonal.ui.navigation.AppNavigation
import com.example.proyectopersonal.ui.navigation.Routes
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoScreen
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoViewModel
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppConMapaScreen
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppMapaListaConMVVM
import com.example.proyectopersonal.ui.screens.cameraScreen.CameraScreen
import com.example.proyectopersonal.ui.screens.medlist.MedListScreen
import com.example.proyectopersonal.ui.screens.settings.SettingsScreen
import com.example.proyectopersonal.ui.theme.ThemeOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var userSettingsViewModel: UserSettingsViewModel //Datastore
        lateinit var addMedicamentoViewModel: AddMedicamentoViewModel //Para usar con BD
    }

    //Al abrir la app se muestra el splashScreen,
//    Se cargan los ajustes de usuario de la app,Theme y Lenguaje desde el DataStore,
//    Y se cargan la lista de medicamentos ya guardados,
//    y se muestra la pantalla principal:
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var showSplashScreen = true
        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        lifecycleScope.launch {
            delay(2000)
            showSplashScreen = false
        }

        super.onCreate(savedInstanceState)

        //Cargar los ajustes guardados de usuario de la app,Theme y Lenguaje desde el DataStore:
        userSettingsViewModel = UserSettingsViewModel()
        userSettingsViewModel.getSettings(applicationContext)

//        Cargar la lista de medicamewntos ya guardados, desde la BD:
//    Lo que hacemos aca, es que invocamos al AddMedicamentoViewModel(context: Context),
//    desde el onCreate() del MainActivity,
//    de inmediato se ejecuta esta fun init, que invoca a la fun getMedicamentos() del DAO,
//    la cual carga la lista de medicamentos desde la BD, y la almacena en la var _medicamentos,
//    que es la lista de medicamentos local, para tenerla disponible para la vista:

        enableEdgeToEdge()
        addMedicamentoViewModel = AddMedicamentoViewModel(applicationContext)

        setContent {
            val dark = userSettingsViewModel.theme == "Dark"
            ProyectoPersonalTheme(
                darkTheme = dark,
                dynamicColor = false
            )
            {
                AppNavigation(
                    skipLogin = true,
                    themeOpt = if (dark) ThemeOption.DARK else ThemeOption.LIGHT,
                    onChangeTheme = { theme ->
                        userSettingsViewModel.theme = when (theme) {
                            ThemeOption.SYSTEM -> "System"
                            ThemeOption.LIGHT -> "Light"
                            ThemeOption.DARK -> "Dark"
                        }
                    }

                )
            }
        }
    }

    //Y antes de cerrar la app, fun onDestroy(), se almacena los datos de conf en el DATASTORE,
    // y las BD en el json o SQLite:en JSON o SQLite, asi:
    override fun onDestroy() {
        super.onDestroy()
//        Guardamos los ajustes de usuario de la app,Theme y Lenguaje en el DataStore:
        userSettingsViewModel.saveSettings(applicationContext)

        //Guardamos la lista de medicamentos en el json:
    }
}


/*// Componente que permite navegar entre pantallas
@Composable
fun AppNavigation(
    skipLogin: Boolean = true,
    onChangeTheme: (ThemeOption) -> Unit = {},
    themeOpt: ThemeOption
) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            IndexScreen(navController)
        }
        composable("med_list") {
            MedListScreen(navController)
        }

        composable("index_detail/{index}") { backStackEntry ->
            //val id = backStackEntry.arguments?.getString("id")
            val index = backStackEntry.arguments?.getString("index")
            //val date = backStackEntry.arguments?.getString("date")
            IndexDetailScreen(navController, index)
        }

        //Aca otro composable para formulario de medicamentos seleccionado:
        composable("add_med") {
            AddMedicamentoScreen(navController)
        }

        composable("map") {
            AppConMapaScreen(
                navController,
                PaddingValues()
            )

        }

        composable("settings") {
            SettingsScreen(
                themeOpt = themeOpt,
                onChangeTheme = onChangeTheme,
                onBack = { navController.popBackStack() }

            )
        }

        composable("map_search") {
            AppMapaListaConMVVM(
                innerPadding = PaddingValues()
            )

        }

        composable("camera") {
            CameraScreen(
                navController,
                PaddingValues()
            )

        }

    }
}*/


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppNavigation(
        themeOpt = ThemeOption.LIGHT

    )
}