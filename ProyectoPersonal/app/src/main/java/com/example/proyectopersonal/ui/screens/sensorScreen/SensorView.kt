package com.example.proyectopersonal.ui.screens.sensorScreen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.proyectopersonal.model.sensores.GyroscopeSensorUIState
import com.example.proyectopersonal.model.sensores.LuxSensorUIState
import com.example.proyectopersonal.model.sensores.MagneticSensorUIState
import com.example.proyectopersonal.model.sensores.MotionSensorUIState
import com.example.proyectopersonal.model.sensores.ProximitySensorUIState
import com.example.proyectopersonal.model.sensores.StepSensorUIState
import com.example.proyectopersonal.viewmodel.SensorViewModel

//ROute: sensorView
//Este archivo es la Pantalla o Vista(composable) que muestra los cambios de los sensores registrados,
//para eso se recurre a las funciones del ViewModel
@Composable
fun SensorView() {
//    Creamos una val de clase MotionSensorViewModel, que es el ViewModel de todos los sensores, No hay un VM para cada sensor:
    val sensorViewModel = SensorViewModel(
        (LocalContext.current.applicationContext as Application)
    )

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

    Scaffold {
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

//            Sensor contyador de pasos:
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