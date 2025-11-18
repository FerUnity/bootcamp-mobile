package com.example.proyectopersonal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Esta es la data class para las Listas de medicamentos,
// que se compone de los medicamentos generados en el ProductData:
// MedsListaData =List<ProductData>

//En ROOM ahora el MedListData se anota como @Entity
// e inmediatamente toma cada prop del MedListData como una columna de la tabla de la BD.
// A medida que se agregan registros se agregan las filas:

@Entity(tableName = "meds_list")
data class MedsListData(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val listName: String,
    val listDescription: String,
    val listCategory: String
)
