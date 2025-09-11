package com.example.proyectopersonal.model

//Esta es la data class para las Listas de medicamentos:
data class MedsListData(
    val id: Int? = null,
    val listName: String,
    val listDescription: String,
    val listCategory: String
)
