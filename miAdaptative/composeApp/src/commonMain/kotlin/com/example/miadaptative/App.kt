package com.example.miadaptative

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

//En vez de usar en App el codigo de Layout por defecto.
//Usaremos aca el codigo del composable AdaptativeScreen(),
//que detectara cuando estemos en Layout Compact, Medium o Expanded.
// Y llamara al Layout correspondiente:
// Mobile, Tablet o Desktop:
@Composable
@Preview
fun App() {
    MaterialTheme {
      /*  var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }*/
        AdaptativeScreen()
    }
}