package com.example.proyectopersonal.room

import com.example.proyectopersonal.model.ProductDAO
import com.example.proyectopersonal.model.ProductData
import com.example.proyectopersonal.services.MedApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Es un cjto o piscina de datos que puedo administrar.
// En este caso administraremos este repositorio desde el ProductDAO:
class MedRepository(private val dao: ProductDAO, private val apiService: MedApiService) {
    val medicamentos: Flow<List<ProductData>> = dao.getAll()

    // Funciones que solo interactúan con el ROOM
    suspend fun addMedicamento(medicamento: ProductData) {
        dao.insertMedicamento(medicamento)
    }

    suspend fun removeMed(medicamento: ProductData) {
        dao.removeMedicamento(medicamento)
    }

    suspend fun updateMed(medicamento: ProductData) {
        dao.updateMedicamento(medicamento)

    }
    // Funciones que solo interactúan con la API
    fun fetchMeds(): Flow<List<ProductData>> = flow {
        val medicamentos = apiService.getMedicamentos()
        emit(medicamentos)
    }

    fun pushMed(medicamento: ProductData): Flow<ProductData> = flow {
        val newMedicamento = apiService.addMedicamento(medicamento)
        emit(newMedicamento)
    }

    fun pushMeds(medicamentos: List<ProductData>): Flow<List<ProductData>> = flow {
        for (medicamento in medicamentos) {
            apiService.addMedicamento(medicamento)
        }
        emit(medicamentos)
    }




    }


