package com.example.proyectopersonal.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Luego esta clase abstracta RoomDatabase, usando @Database, represernta a la BD SQLite o la que sea:
//Aca unimos todas las clases: entidades + Dao:

@Database(entities = [ProductData::class, MedsListData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "meds_list.db"

//        @Volatile
//        private var db: AppDatabase? = null
//        fun getInstance(context: Context): AppDatabase {
//            if (db == null) {
//                db = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                )
//                    .build()
//            }
//            return db!!
//        }

    }


    //    Me pide implementar los 2 prodDAO como abstractas: 2 fun abstractas que devuelven los DAOs de cada entidad.
//    Para poder operar con la BD:
    abstract fun productDao(): ProductDAO
    abstract fun medShoppingListDao(): MedShoppingListDAO

}