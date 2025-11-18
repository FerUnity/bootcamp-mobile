package com.example.proyectopersonal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Esta es la data class de los medicamentos propiamente tal.
// Y que luego componen las listas de medicamentos:

//En ROOM ahora el ProductData se anota como @Entity
// e inmediatamente toma cada prop del ProductData como una columna de la tabla de la BD,
// // A medida que se agregan registros se agregan las filas:
@Entity(tableName = "medicamentos")
data class ProductData(
//    Ademas el id sera una primaryKey:
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val nombre: String,
    val marca: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val medListId: Int? = null //Que es el ID pero de la lista de medicamentos: MedsListData


)