package com.example.proyectopersonal.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyectopersonal.MainActivity.Companion.userSettingsViewModel
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.ui.theme.Green
import com.example.proyectopersonal.ui.theme.ProyectoPersonalTheme


class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dark = userSettingsViewModel.theme == "Dark"
            ProyectoPersonalTheme(
                darkTheme = dark,
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeScreenContent(
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreenContent(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, Color.Gray),
                    startY = 0f,
                    endY = 900f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.mipmap.ic_bg_hospitales_foreground),
            contentDescription = "Hospital",
            contentScale = ContentScale.Fit,
            //Se tiene definir el tamaño asi:
            modifier = Modifier
                .fillMaxWidth()
//            Tecortar la img circularmente para que no se vea el borde cuadrado
//            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "BIENVENIDO A LA APP!",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

//        Boton para crear cuenta: Sign Up Free:
        Button(
            onClick = {navController.navigate("home") }, //Usando el param de la fun
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp) //Alto de boton
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        )
        {
            Text(
                text = "INICIAR", //HOME
                color = Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}