package com.example.proyectopersonal.ui.screens.cameraScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.proyectopersonal.model.MediaUIState
import com.example.proyectopersonal.viewmodel.MediaViewModel
import java.io.File

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun CameraFunction(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current
    val mediaViewModel: MediaViewModel = MediaViewModel()

//    La sgte val es reactiva se basa en los 4 estrados que se presentan en la clase MediaUIState,
//    idle, loading, success, error.
//    Accedemos a los 4 estados UI desde la clase MediaViewModel:
    val uiState by mediaViewModel.mediaUIState.collectAsState()
    var tempUri by remember { mutableStateOf<Uri?>(null) }

//    El sgte codigo de val cameraLauncher, es el que se aplica al hacer click al btn CAMERA.
//    Lo demas son permisos.
//    Aca creamos este val para poder abrir la app propia de Android para usar la camara y decirle que queremos una imagen
//    y donde la queremos guardar(tempUri) usando la fun onMediaCaptured de la clase MediaViewModel:
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
//        Exito -> Si la captura de la foto fue exitosa y existe su URI:
//        Entonces llamamos a la fun onMediaCaptured del viewModel y le pasamos el URI:
        if (success && tempUri != null) {
            mediaViewModel.onMediaCaptured(tempUri!!)
        } else {
            mediaViewModel.onError("Error capturing image")
        }
    }
    //    El sgte codigo de val galleryLauncher, es el que se aplica al hacer click al btn GALLERY.
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            mediaViewModel.onMediaCaptured(uri)
        } else {
            mediaViewModel.onError("Error selecting image")
        }
    }

//    Permiso para usar la app propia de Android para usar la camara, No para la Gallery porque no se necesita Permisos, creo.
//    Se abre un cuadro de dialogo que solicita permisos:
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        //Si se autoriza usar la camara, llamamos a la fun createImageUri,
        // para crear un archivo de imagen, en el espacio reservado para guardar la foto tomada con la camara.
        if (isGranted) {
            val uri = createImageUri(context)
            tempUri = uri
            cameraLauncher.launch(uri) //Que se abra la app propia de Android para usar la camara
        } else {
            mediaViewModel.onError("Camera permission denied")
        }
    }

//    Scaffold{
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            Row {
//                Boton que abre la app propia de Android para usar la camara:
                Button(
                    onClick = {
//                        En esta config, al hacer click en ese btn Camera,
                        // simplemente pido que se abra la app propia de Android para usar la camara,
                        // o sea no estoy usando una API propia:
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(0.5f)
                ) {
                    Text("Camera")
                }
//                Boton que abre la galeria:
                Button(
                    onClick = {
                        galleryLauncher.launch("image/*")
                              //Para acceder solo a las img de la Galeria, no a otros docs
//                        Si quiero a cualq tipo de docs: galleryLauncher.launch("*/*")
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(0.5f)
                ) {
                    Text("Gallery")
                }
            }

//            Cuando se abre la app, se mostraram los 4 estados UI
            when (uiState) {
                is MediaUIState.Idle -> Text("Waiting for action...")
                is MediaUIState.Loading -> CircularProgressIndicator() //Circulo cargando
                is MediaUIState.Success -> {
                    val uri = (uiState as MediaUIState.Success).uri
                    Log.d("CameraScreen", "uri: $uri") //Para ver la ruta de almac de las fotos tomadas con camara
//                    Luego se cargara una img asuncrona que se cargara desde la uri que se guardo en el dispositivo
                    AsyncImage(
                        model = uri,
                        contentDescription = "Selected or captured image"
                    )
                    /* GlideImage(
                        model = uri,
                        contentDescription = "Selected or captured image"
                    ) */
                }
                is MediaUIState.Error -> Text("Error: ${(uiState as MediaUIState.Error).message}")
            }
        }
//    } //FIn Saffold
}

//    Con esta fun, al hacer click al btn Camera se abrira la app propia de Android para usar la camara,
//    entonces se llamara a esta fun que luego de tomar la foto creara un file donde se guardara la mfoto tomada.
//     Luego retorna la uri donde se guardo el archivo
fun createImageUri(context: Context): Uri {

    val file = File(
        context.cacheDir,
        "photo_${System.currentTimeMillis()}.jpg"
    )
//    El FileProvider que estable el Uri donde se guarda la foto,
//    se debe config en el AndroidManifest.xml

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}