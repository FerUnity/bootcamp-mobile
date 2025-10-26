package com.example.proyectopersonal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectopersonal.model.firebase.Artist
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.viewmodel.HomeViewmodel

//Aqui se mostraran y añadiremos artistas a la BD Firestore
//@Preview
@Composable
fun HomeScreen(
    navcontroller: NavController,
    homeViewModel: HomeViewmodel = HomeViewmodel()
) {
//    Boton para añadir artistas a la BD de Firestore:
    /* Button(
 //        Llamamos a la fun createArtist() que creara un artista en la BD:
         onClick = {
             createArtist(db)
         }

     ) {
         Text(text = "Añadir artista")
     }*/

//    Para que se vaya actualizando la lista de artistas obtenida en el VModel, desde la BD de Firestore
    //    frente a cualquier cambio en tiempo real,
    //    usamos collectAsState()
//    val artists = homeViewModel.artist.collectAsState()
    val artists = homeViewModel.artist.collectAsState()
//
//    val player: Player? by viewModel.player.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(rememberScrollState())
    )
    {
        Text(
            text = "Noticias",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 2.dp)
                .weight(0.2f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "(Firestore)",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp)
                .weight(0.2f),
            textAlign = TextAlign.Center
        )

//        Creamos una lista falsa de artistas de la clase Artist
//        val artists = emptyList<Artist>()

//        Para mostarr la lista de artistas obtenidas desde la BD (desde el VModel), en filas horizontales, usamos un LazyRow:
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
//            Para los items, usamos como param it, la lista de objetos artistas, de clase Artist,
            // colectada de la lista "artists" del FireStore,
            // y obtenida en la clase HomeViewmodel() para usarse localmente.
            // Luego esa lista de artistas la pasamos como param en la fun ArtistItem(artists) que muestra a los artistas,
            // cada elemento es recorrido por items() y se muestra en una fila horizontal:
            items(artists.value) {
//                LLamamos a la fun que muestra a los artistas con imagen y nombre de forma vertical:
                ArtistItem(artist = it)
            }
        }

        Spacer(
            modifier = Modifier
                .weight(0.2f)
        )
        /*if(player != null) {
            val color = if(player!!.play == true) Color.Green else Color.Red
            Text(
                text = player?.artist?.name.orEmpty(),
                color = color)
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Green),
            ){
                Text(text = player?.artist?.name.orEmpty())
//                    Creamos una caja que cambiara su color si esta en play(verde) o pause(Rojo):
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color)
                )



            }

            }*/

//        Podriamos poner un boton o texto clickable aca para acceder a la pantalla de la fun composable
//        RtdbScreen()
        Text(
            text = "Ir a RTDB",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .weight(0.5f)
                //El texto Log In tendra la caract de boton en que al hacer click navega a la pantalla de login:
                .clickable { navcontroller.navigate("rtdbScreen") },
        )

    }

}

//Creamos una fun que muestra la lista de objetos artistas, de clase Artist,
// colectada de la lista "artists" del FireStore,
// con imagen y nombre de forma vertical,:
@Composable
fun ArtistItem(artist: Artist) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
//       Para reempl la img ponemos una box de color rojo:}
        /*Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Red)
        )*/
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = artist.image, //Llamamos a la img del artista desde la data class Artist
            contentDescription = "Artist image"
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = artist.name.orEmpty(),//llamamos al nombre desde la data class Artist
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = artist.description.orEmpty(),//llamamos  a la descripcion desde la data class Artist
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )

    }
}


//fun para crear un artista, en funcion de los campos de la data class Artist:
/*
fun createArtist(db:FirebaseFirestore){
    val random = (1..10000).random()
    val artist = Artist(
        name = "Artista $random",
        description = "Descripcion $random",
        image = "https://picsum.photos/200",
        songs = emptyList()

    )
//    Luego añadimos el artista a la coleccion artists de la BD Firestore:
    db.collection("artists")
        .add(artist)
        .addOnSuccessListener {
            println("Artista creado con exito")
        }
        .addOnFailureListener {
            println("Error al crear el artista")
        }
        .addOnCompleteListener {
            println("Completado") //Indep si success o con error

        }
}*/
