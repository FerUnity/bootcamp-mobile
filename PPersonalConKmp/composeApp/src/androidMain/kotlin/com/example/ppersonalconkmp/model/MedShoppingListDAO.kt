package com.example.proyectopersonal.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//    SI SE USA CON ROOM:
//    Luego tiene que haber 1 @Dao por data class o Entidad,
//    esta interface  ADMINISTRA LA TABLA DE LISTAS MEDICAMENTOS CON MedsListData.kt.
//    Room automaticamente implementa la comunicacion con la BD.
//    las anotaciones @Query, @Inser, @Update, @Delete le indican a Room que hacer con cada funcion que sigue abajo.
//    Ojo ademas cada fun debe funcionar como asincrona con corrutina(suspend) para que no bloquee la interfaz con el usuario:
@Dao
interface MedShoppingListDAO {

//    Insertar una lista de compras de medicamentos (list: MedsListData) en la tabla meds_list de la BD:
    @Insert
    suspend fun createList(list: MedsListData): Long

//    Actualizar una lista de compras de medicamentos (list: MedsListData) en la tabla meds_list de la BD:
    @Update
    suspend fun updateList(list: MedsListData): Int

//    Esta fun se carga desde la fun loadMedsList() en AddMedicamentoViewModel.kt,
    @Query("SELECT * FROM meds_list")
    suspend fun getMedShoppingLists(): List<MedsListData>





    // ESTAS FUN SON SIN ROOM. COMMIT ANTERIOR:
    //Estas 2 fun es para crear una lista de compras de medicamentos, por ende actua sobre la tabla meds_list,
    // o sea sobre MedsListData.
//    fun createList(list: MedsListData): Long
//
//    fun updateList(list: MedsListData): Long
//
//    //Y estas fun actuan sobre la tabla de medicamentos, por ende sobre ProductData:
//    fun addMedicamento(medicamento: ProductData): Long
//
//    fun getMedicamentosById(id: Int): ProductData?
//
//    //Pero estas 2 fun obtendran una lista de medicamentos de una lista de compras de medicamentos,
//    fun getMedicamentos(medListId: Int): List<ProductData>
//
//    fun getMedicamentosByCategory(medListId: Int, category: String): List<ProductData>
//
//    fun updateMedicamento(medicamento: ProductData): Long
//
//    fun removeMedicamento(medicamento: ProductData): Long

}