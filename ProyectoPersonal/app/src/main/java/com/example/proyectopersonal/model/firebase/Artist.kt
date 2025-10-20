package com.example.proyectopersonal.model.firebase

//Esta data class repr la info guardada en el Firestore
data class Artist(
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
//    val songs: List<Song> //La lista de canciones de ese artista estan en una data class Song
)
