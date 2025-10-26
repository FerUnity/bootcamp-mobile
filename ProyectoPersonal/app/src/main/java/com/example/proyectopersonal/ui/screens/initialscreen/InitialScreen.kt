package com.example.proyectopersonal.ui.screens.initialscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.theme.BackgroundButton
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.ui.theme.Gray
import com.example.proyectopersonal.ui.theme.Green
import com.example.proyectopersonal.ui.theme.ShapeButton

//Pantalla incial de la app:
@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
//    La fun tiene como parm 2 fun lambda para establecer la ruta al click botones
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.Black,Color.Gray), startY = 0f, endY = 900f)),
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
            text = "Sistema de Orientación Hospitalaria",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Santiago Centro",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

//        Boton para crear cuenta: Sign Up Free:
        Button(
            onClick = { navigateToSignUp() }, //Usando el param de la fun
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
                text = "REGISTRARSE", //Sign up
                color = Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

//        Llamamos a la fun para crear un boton personalizado con la img de google:
        CustomButton(
            Modifier.clickable {/*URL que sea*/ },
            painterResource(id = R.drawable.google),
            "Log in with Google"
        )

        Spacer(modifier = Modifier.height(8.dp))

//        Llamamos a la fun para crear un boton personalizado con la img de facebook:
        CustomButton(
            Modifier.clickable {/*URL que sea*/ },
            painterResource(id = R.drawable.facebook),
            "Log in with Facebook"
        )

//        TEXTOS CLICKABLE:
        Text(
            text = "Log In",
            color = Color.White,
            modifier = Modifier
                .padding(24.dp)
                //El texto Log In tendra la caract de boton en que al hacer click navega a la pantalla de login:
                .clickable { navigateToLogin() },
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Entrar como Invitado",
            color = Color.White,
            modifier = Modifier
                .padding(24.dp)
                //El texto Log In tendra la caract de boton en que al hacer click navega a la pantalla de login:
                .clickable { navigateToHome() },
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

    }

}

//fun composable para botones personalizados con iconos de GOOGLE y FACEBOOK:
@Composable
fun CustomButton(modifier: Modifier, paint: Painter, title: String) {
    Box(
        modifier = modifier //parametro que recibe el modificador
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(BackgroundButton, shape = CircleShape) //Color de fondo del boton
            .border(2.dp, ShapeButton, shape = CircleShape) //Borde del boton
        ,
        contentAlignment = Alignment.CenterStart
        //El texto siempre estara centrado aunque se ponga una img o no al boton al inicio
    )
    {
        Image(
            painter = paint, //parametro que recibe la img
            contentDescription = "Google",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )


        Text(
            text = title, //parametro que recibe el texto
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )
    }
}
