package com.example.midemo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.midemo.viewmodel.Greeting
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import midemo.composeapp.generated.resources.Res
import midemo.composeapp.generated.resources.compose_multiplatform
import midemo.composeapp.generated.resources.greeting
import midemo.composeapp.generated.resources.logo_artemayorvertical
import midemo.composeapp.generated.resources.welcome_message
import org.jetbrains.compose.resources.stringResource

//Este composable es la vista de este proyecto. La idea es que llamara a la fun greet()
//del ViewModel Greeting la cual llama a la fun getPlatform() que es distinta en cada SourceSet
@Composable
@Preview
fun App() {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var name by remember { mutableStateOf("") }
        Row {
//      La idea es que el nombre que ingresemos en el textfield, aparezca en el saludo
            TextField(
                value = name,
                onValueChange = { name = it }
            )
            Button(
                onClick = { showContent = !showContent }
            )
            {
//                Text("Click me!")
//                Text("Saludame!!")
                Text(stringResource(Res.string.welcome_message)) //Welcome, %1$s!
            }

        }

        AnimatedVisibility(showContent) {
//                En la animacion llamamos a la fun greet(),
            //                del ViewModel Greeting, que retorna el mensaje a mostrar con el Logo
            val greeting = remember { Greeting().greet(name) }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                La imagen se toma del Res del commonMain:
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text(
                    color = Color.Black,
                    text = "Compose: $greeting"
                )
            }
        }
    }
}