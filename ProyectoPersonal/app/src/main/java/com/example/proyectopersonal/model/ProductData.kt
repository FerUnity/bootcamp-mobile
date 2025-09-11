package com.example.proyectopersonal.model

//Esta es la data class de los medicamentos propiamente tal.
// Y que componen las listas de medicamentos:
data class ProductData(
    val id: Int,
    val nombre: String,
    val marca: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val medListId: Int? = null //Que es el ID pero de la lista de medicamentos: MedsListData


)