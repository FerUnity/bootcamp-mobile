package com.example.proyectopersonal.model

interface AddMedicamentoDAO {
    fun addOrUpdateMedicamento(medicamento: ProductData)
    fun getMedicamentosById(id: Int): ProductData?
    fun getMedicamentos(): List<ProductData>
    fun removeMedicamento(medicamento: ProductData)

}