package com.example.proyectopersonal.room

import com.example.proyectopersonal.model.ProductDAO
import com.example.proyectopersonal.model.ProductData
import kotlinx.coroutines.flow.Flow

//Es un cjto o piscina de datos que puedo administrar.
// En este caso administraremos este repositorio desde el ProductDAO:
class MedRepository(private val dao: ProductDAO) {
    val medicamentos: Flow<List<ProductData>> = dao.getAll() as Flow<List<ProductData>>

    suspend fun addMedicamento(medicamento: ProductData) {
        dao.insertMedicamento(medicamento)
    }

    suspend fun removeMed(medicamento: ProductData) {
        dao.removeMedicamento(medicamento)
    }

    suspend fun updateMed(medicamento: ProductData) {
        dao.updateMedicamento(medicamento)

    }

    }


