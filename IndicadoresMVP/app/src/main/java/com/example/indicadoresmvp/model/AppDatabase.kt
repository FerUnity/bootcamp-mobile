package com.example.indicadoresmvp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Luego esta clase abstracta RoomDatabase, usando @Database, represernta a la BD SQLite o la que sea:
//Aca unimos todas las clases: entidades + Dao:

@Database(entities = [Indicador::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "meds_list.db"

        //    El DatabaseBuilder es un objeto singleton que se utiliza para obtener una instancia de la base de datos,
        //    en tiempo de ejecucion de la app
//        object DatabaseBuilder {
        //Creamos una var de clase BD que referencia a la clase abstracta AppDatabase, por ende a la BD Sqlite:
        @Volatile private var db: AppDatabase? = null

        //    Obtenemos una instancia de la BD para que sea sigleton:
        fun getDatabase(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    //Aca ponemos el nombre de la BD:
                    DATABASE_NAME
                ).build()
            }
            return db!!

        }
//        }



    }


    //    Me pide implementar los 2 prodDAO como abstractas: 2 fun abstractas que devuelven los DAOs de cada entidad.
//    Para poder operar con la BD:
//    abstract fun productDao(): ProductDAO
//    abstract fun medShoppingListDao(): MedShoppingListDAO

}