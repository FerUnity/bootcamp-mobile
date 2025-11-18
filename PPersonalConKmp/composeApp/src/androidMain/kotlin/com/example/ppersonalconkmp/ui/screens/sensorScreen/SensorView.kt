package com.example.proyectopersonal.ui.screens.sensorScreen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.sensores.GyroscopeSensorUIState
import com.example.proyectopersonal.model.sensores.LuxSensorUIState
import com.example.proyectopersonal.model.sensores.MagneticSensorUIState
import com.example.proyectopersonal.model.sensores.MotionSensorUIState
import com.example.proyectopersonal.model.sensores.ProximitySensorUIState
import com.example.proyectopersonal.model.sensores.StepSensorUIState
import com.example.proyectopersonal.ui.components.IndexTopBar
import com.example.proyectopersonal.viewmodel.SensorViewModel
import kotlinx.coroutines.CoroutineScope

//ROute: sensorView
//Este archivo es la Pantalla o Vista(composable) que muestra los cambios de los sensores registrados,
//para eso se recurre a las funciones del ViewModel:
@Composable
fun SensorView(
    navController: NavHostController,
    innerPadding: PaddingValues,
    sensorViewModel: SensorViewModel = viewModel()
    //Creamos var sensorViewModel de tipo SensorViewModel y el viewModel() es para que sea persistente
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //El snackBar necesita estos 2 val:
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
//    Creamos una val de clase MotionSensorViewModel, que es el ViewModel de todos los sensores, No hay un VM para cada sensor:
   /* val sensorViewModel = SensorViewModel(
        (LocalContext.current.applicationContext as Application)
    )*/

//    Ref a cada variable donde se colectan los cambios de cada sensor, (collectAsState)
//    segun los estados def en la data class (UIState) de cada sensor:
    val motionSensorUIState: State<MotionSensorUIState> =
        sensorViewModel.motionSensor.collectAsState()
    val proximitySensorUIState: State<ProximitySensorUIState> =
        sensorViewModel.proximitySensor.collectAsState()
    val magneticSensorUIState: State<MagneticSensorUIState> =
        sensorViewModel.magneticSensor.collectAsState()
    val luxSensorUIState: State<LuxSensorUIState> =
        sensorViewModel.luxSensor.collectAsState()
    val gyroscopeSensorUIState: State<GyroscopeSensorUIState> =
        sensorViewModel.gyroscopeSensor.collectAsState()
    val stepSensorUIState: State<StepSensorUIState> =
        sensorViewModel.stepSensor.collectAsState()

//    Usando 2 corrutinas llamamos a las 2 funciones starListening() y stopListening() del ViewModel:

//    Parta iniciar el listener:
    LaunchedEffect(Unit) {
        sensorViewModel.starListening()
    }

//    Para dar de baja al listener:
    DisposableEffect(Unit) {
        onDispose {
            sensorViewModel.stopListening()
        }
    }

    Scaffold(
        //Que el scaffold tenga un topBar
        topBar = {
            //Llamamos a la fun IndexTopBar() del archivo IndexTopBar.kt que arma el topBar:
            IndexTopBar(
                navController, drawerState, scope, stringResource(R.string.app_name)
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        //Llamamos a la fun FAButton() del archivo FAButton.kt que arma el FAB:
        // Boton flotante redondo, rojo con el singo +:
//        floatingActionButton = {
//            FAButton()
//        },

        //Que el scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
        //Conten del Scaffold:
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor der Movimiento o Acelerometro:
            Text(
                text = "Accelerometer Sensor",
                modifier = Modifier.padding(start = 16.dp)
            )
//            Creamos una fila con los 3 textos, para cada eje del acelerometro:
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
//                Se hace ref a la var motionSensorUIState del VModel
                //que aca muestra los valores de acelerometro colectados:
                Text(
                    text = "X: ${String.format("%.1f", motionSensorUIState.value.x)} m/s2",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Y: ${String.format("%.1f", motionSensorUIState.value.y)} m/s2",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Z: ${String.format("%.1f", motionSensorUIState.value.z)} m/s2",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor proximidad
            Text(
                text = "Proximity Sensor",
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "d: ${String.format("%.1f", proximitySensorUIState.value.distance)} cm",
                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor campo magnetico
            Text(
                text = "Magnetic Sensor",
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "X: ${String.format("%.1f", magneticSensorUIState.value.x)} uT",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Y: ${String.format("%.1f", magneticSensorUIState.value.y)} uT",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Z: ${String.format("%.1f", magneticSensorUIState.value.z)} uT",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor de luminosidad:
            Text(
                text = "Light Sensor",
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "lux: ${String.format("%.1f", luxSensorUIState.value.lux)} lx",
                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor giroscopio:
            Text(
                text = "Gyroscope Sensor",
                modifier = Modifier.padding(start = 16.dp)
            )
            Row(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = "X: ${String.format("%.1f", gyroscopeSensorUIState.value.x)} rad/s",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Y: ${String.format("%.1f", gyroscopeSensorUIState.value.y)} rad/s",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Z: ${String.format("%.1f", gyroscopeSensorUIState.value.z)} rad/s",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

//            Sensor contador de pasos:
            Text(
                text = "Step Counter",
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Steps: ${stepSensorUIState.value.steps}",
                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}