package com.example.proyectopersonal.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.model.firebase.Player
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.viewmodel.RtdbViewModel

//Esta pantalla es la que muestra la info obtenid en el RTDB y que fue colectada por el VModel correspondiente:

//Composable: "rtdbScreen".Se llama desde un texto clickableen lapantalla HomeScreen.
@Composable
fun RtdbScreen(rtdbViewModel: RtdbViewModel = viewModel(),
                navController: NavController)
{
    val player: Player? = rtdbViewModel.player.collectAsState().value as Player?
//    val player: Player? = rtdbViewModel.player.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(rememberScrollState())
    )
    {
        Text(
            text = "Artistas en la nube",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )


        /*   val color = if (player.play == true) Color.Green else Color.Red
           Text(
               text = player.artist?.name.orEmpty(),
               color = color
           )
           Row(
               modifier = Modifier
                   .height(50.dp)
                   .fillMaxWidth()
                   .background(Color.Cyan),
               verticalAlignment = Alignment.CenterVertically
           ) {
               Text(text = player.artist?.name.orEmpty())
               Spacer(
                   modifier = Modifier
                       .weight(1f)
               )
//        Creamos una caja pequeña que cambiara su color si la var play(boole) en RTDB,
//        esta en play(verde) o pause(Rojo),
//        ademas la caja sera clickable, llamando a la fun rtdbViewModel.onPlaySelected(),
//        que hara que al pres el box cambie de color porque cambiar el valor del bool play en el RTDB.
               Box(
                   modifier = Modifier
                       .size(20.dp)
                       .background(color)
                       .clickable{rtdbViewModel.onPlaySelected()}
               )


           }*/

//        Si existe un player, que siempre deberia haber,
//        que llame a la fun que trae a la box que cambia de color
//        al hacer true o false la var play del data class Artist,
//        ya no la box sino que un btn que camboiara suis eatdo segin sea play o false o stop.
        player?.let {
            PlayerComponent(player = it,
                onPlaySelected = {rtdbViewModel.onPlaySelected()},
                onCancelSelected = {rtdbViewModel.onCancelSelected()})
        }

    }

    Button(
        onClick = {
           navController.navigate("home")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        Text(text = "Opciones de App")

    }
}

@Composable
fun PlayerComponent(player: Player, onPlaySelected: () -> Unit, onCancelSelected: () -> Unit){
    val color = if (player.play == true) Color.Green else Color.Red
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Color.Cyan),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = player.artist?.name.orEmpty(), //Es el nombre del artista en el RTDB
            modifier = Modifier
                .padding(horizontal = 12.dp),
            color = Color.White
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
//        Creamos una caja pequeña que cambiara su color si la var play(boole) en RTDB,
//        esta en play(verde) o pause(Rojo),
//        ademas la caja sera clickable, llamando a la fun rtdbViewModel.onPlaySelected(),
//        que hara que al pres el box cambie de color porque cambiar el valor del bool play en el RTDB.
        /*  Box(
              modifier = Modifier
                  .size(20.dp)
                  .background(color)
                  .clickable{rtdbViewModel.onPlaySelected()}
          )*/
        val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play

        Image(painter = painterResource(icon),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(40.dp)
                .clickable {onPlaySelected()}
        )
        Image(painter = painterResource(R.drawable.ic_close),
            contentDescription = "close",
            modifier = Modifier
                .size(40.dp)
                .clickable {onCancelSelected()}
        )




    }

}
