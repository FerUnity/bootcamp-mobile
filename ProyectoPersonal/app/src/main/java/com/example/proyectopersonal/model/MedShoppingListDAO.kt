package com.example.proyectopersonal.model

//AMbas tablas: meds_list y medicamentos se administran en el mismo DAO porque estan relacionadas
interface MedShoppingListDAO {
    //Estas 2 fun es para crear una lista de compras de medicamentos, por ende actua sobre la tabla meds_list,
    // o sea sobre MedsListData:
    fun createList(list: MedsListData): Long

    fun updateList(list: MedsListData): Long

    //Estas fun actuan sobre la tabla de medicamentos, por ende sobre ProductData:
    fun addMedicamento(medicamento: ProductData): Long

    fun getMedicamentosById(id: Int): ProductData?

    //Pero estas 2 fun obtendran una lista de medicamentos de una lista de compras de medicamentos,
    fun getMedicamentos(medListId: Int): List<ProductData>

    fun getMedicamentosByCategory(medListId: Int, category: String): List<ProductData>

    fun updateMedicamento(medicamento: ProductData): Long

    fun removeMedicamento(medicamento: ProductData): Long

}