package com.example.proyectopersonal.ui.screens.notificacionScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.notificacionesModel.SyncWorker
import com.example.proyectopersonal.ui.components.IndexTopBar
import com.example.proyectopersonal.viewmodel.BackgroundViewModel
import com.example.proyectopersonal.viewmodel.NotificationViewModel
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.TimeUnit

//Esta pantalla es la vista de todos los botones de notificaciones:
// para compartir un texto, audio, imagen, etc. con otra app: correo, whatsapp, etc.
//Hacer notificaciones push
// HAcer seguimientos
//Hacer Workers o tareas programadas a realizarse en segundo plano
@Composable
fun ExamplesScreen(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = NotificationViewModel(),
    backgroundViewModel: BackgroundViewModel = BackgroundViewModel(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //El snackBar necesita estos 2 val:
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val context: Context = LocalContext.current
//    Cremoas 2 var string del titulo y mensaje a compartir,
//    pero como objetos para pooder copartirlos con otras app,
//    no como un string:
    var titleToShare: TextFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var textToShare: TextFieldValue by remember { mutableStateOf(TextFieldValue()) }
//    var bool(flag) para saber si esta corriendo o no el backgroundViewModel:
    var isRunning: Boolean by remember { mutableStateOf(false) }

//    Para sincronizar datos en segundo plano: llamamos a la clase SyncWorker.
//    Si queremos que la sync solo ser haga 1 vez
    val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()

    //    Pero si queremos que la msync se haga varias veces cada cierto tiempo:
    //    o sea por ej: que se ejecute 15 veces la sync y que cada sync se haga cada 1 hora o cada 1 dia, etc:
    val timedWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.SECONDS).build()

//    Para auth con biometria, huella digital. Colectamos los estados del uiState obtenidos en el biometricViewModel:
//    val state by biometricViewModel.uiState.collectAsState()


//    Ejec el LaunchedEffect que se ejec al iniciar esta pantalla,
//    la idea es que apenas inicie esta pantalla se genere el canal de notif, para recibir notif.
//    Usamos la fun del viewModel para crear el canal:
    LaunchedEffect(Unit) {
        notificationViewModel.createNotificationChannel(context)
    }


//    Hacemos codigo para pedir permiso de notificacion con un cuadro de dialogo, si es que no tenemos permiso para notificar:
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("ExamplesScreen", "Permiso concedido")
        } else {
            Log.d("ExamplesScreen", "Permiso denegado")
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
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding) //Para que quede dentro del scaffold
                .fillMaxSize(),
//            Para que cada elem quede separado del tro en 16 dp
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
//            Si es que se autentifico con biometria se activan todos los campos:
//            if (state.authenticated) {
//       Titulo que se muestra en un TextField, para el mensaje a compartir
            TextField(
                value = titleToShare,
                onValueChange = { titleToShare = it },
                label = { Text("Titulo para compartir") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            //            El texto a copartir se muestra en un text field
            TextField(
                value = textToShare,
                onValueChange = { textToShare = it },
                label = { Text("Texto para compartir") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp), //Para separar los elem del Row: 2 botones
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //            Boton para compartir el texto dl TextField:
                Button(
                    onClick = {
//                    Aca hacemos el codigo para compartir el texto:
//                    Creamos un intent para compartir:
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND //para compartir un solo elemento
                            type = "text/plain" //tipo de dato a compartir: texto plano.
//                        Hay otros type: "image/*": para imagenes,  putExtra(Intent.EXTRA_STREAM,uri)
//                        "audio/*": para audio, etc.  Pero el putExtra tiene que ser el correspondiente.
//                        Luego con putExtra, ponemos el texto a compartir: el obj textToShare como texto:
                            putExtra(Intent.EXTRA_TEXT, textToShare.text)
//                        Podemos agregar datos adicionales al compartir:
                            //titulo al mensaje de correo. Aparecera solo msi se comparte a un correo:
                            putExtra(Intent.EXTRA_SUBJECT, titleToShare.text)
                            putExtra(Intent.EXTRA_TITLE, titleToShare.text)

                            /* putExtra(Intent.EXTRA_TEXT, "Texto compartido")
                         putExtra(Intent.EXTRA_STREAM, URI("https://www.google.com"))*/

//                         Hay otros type: "image/*": para imagenes,
                            //    putExtra(Intent.EXTRA_STREAM,uri)

//                        Para copartir varios elemntos hacemos:
                            /* action = Intent.ACTION_SEND_MULTIPLE
                         type = "image/ *" OJO sin espacio
 //                        Si son varias imagenes hay que agrergar un array con las uri(s) de las imagenes:
                         putExtra(Intent.EXTRA_STREAM, ArrayList<URI>())*/

                        }
//                    Crwamos ahora un intent para elegir las app a usar, aparecera un txt Compartir con...:
                        val chooserIntent = Intent.createChooser(
                            shareIntent,
                            "Compartir con...."
                        )
                        context.startActivity(chooserIntent) //Ejecutamos comparticion
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)

                ) {
                    Text(text = "Compartir")
                }

//                Boton para enviar las notificaciones push
                Button(
                    onClick = { //Si el titulo y el mensaje no estan vacios.
                        if (titleToShare.text.isNotEmpty() && textToShare.text.isNotEmpty()) {
//                           Verificamos si tenemos permiso para notificar:
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                            Y validamos los permisos aca:
                            if (ActivityCompat.checkSelfPermission( //Si no tenemos permiso para notificar:
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
//                                Mostrar mensaje logcat:
                                Log.d("ExamplesScreen", "No se tienen permisos para notificar")

                            }
//                            Si es que tenemos permiso para notificar:
                            //La app ENVIA el mensaje a la band de entrada del dispositivo:
                            else {
                                notificationViewModel.showNotification(
                                    context,
                                    titleToShare.text,
                                    textToShare.text
                                )
                            }

                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)

                ) {
                    Text(text = "Notificar")
                }

            } //Cierre Row

//            Boton para iniciar o detener el servicio de seguimiento de info del usuario: Geoloc, etc
//            Creo que es un conteo hasta 10
            Button(
                onClick = {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d("ExamplesScreen", "No se tienen permisos para notificar")
                    }
//                    Si es que si hay permisos que se ejecute el codigo de seguimiento de info del usuario:
                    else {
//                    Llama a las fun de inicio y detencion de seguimiento del backgroundViewModel:
                        if (isRunning) {
                            backgroundViewModel.stopTracking(context)
                        } else {
                            backgroundViewModel.startTracking(context)
                        }
//                    Al pres el boton la var flag isRunning, cambiara de true a false y viceversa:
                        isRunning = !isRunning
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(if (isRunning) "Detener" else "Iniciar")
            }

            // Boton para iniciar o detener servicio de Sincronizar datos en segundo plano:
            Button(
                onClick = {
                    WorkManager.getInstance(context)
                        .enqueue(syncWorkRequest) //La sync se ejecuta una vez
                    WorkManager.getInstance(context)
                        .enqueue(timedWorkRequest) //La sync se ejecuta programada. Cada 1 SEGUNDO, por 15 veces


                },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text("Sincronizar datos")
            }
        }
        /*else {
            //Si no esta auntenticado,
            // que aparezca el boton para autenticar biometricamente con huella digital:
            Button(
                onClick = {
                    onAuthenticate()

                },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text("Autenticar con Huella Digital")
            }


        }*/
    }

}