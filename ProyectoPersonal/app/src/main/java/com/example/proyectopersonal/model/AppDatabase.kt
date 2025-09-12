package com.example.proyectopersonal.model

import androidx.room.Database
import androidx.room.RoomDatabase

//Luego esta clase abstracta RoomDatabase, usando @Database, represernta a la BD SQLite o la que sea:
//Aca unimos todas las clases: entidades + Dao:


abstract class AppDatabase: RoomDatabase() {
//    Me pide implementar los 2 prodDAO como abstractas: 2 fun abstractas que devuelven los DAOs de cada entidad.
    //    Para poder operar con la BD:
    abstract fun productDao(): ProductDAO
    abstract fun medShoppingListDao(): MedShoppingListDAO

}