package com.example.persistenciaynetworkingmultiplatform

import com.example.persistenciaynetworkingmultiplatform.ktor.UserApi
import com.example.persistenciaynetworkingmultiplatform.ktor.UserApiService
import com.example.persistenciaynetworkingmultiplatform.sqldelight.Database

//El Repository es tb un intermediario para acceder a las fun del ApiService,
// que se conecta directamente con los servicios remotos. Abstrayendo al Repository de esa funcion:
class UserRepository(
    private val api: UserApiService,
    private val database: Database

) {
    //Funcion para obtener una lista con los usuarios desde la API:
    suspend fun getUsersFromApi(): List<UserApi> = api.getUsers()

    //Funcion para obtener los usuarios una lista con desde la BBDD local:
//    fun getUsersFromDb() = database.userQueries.selectAllUsers().executeAsList()
    suspend fun getUsersFromDb(): List<UserApi> = api.getUsers()
    //Es como lo mismo que el getUsersFromApi() pero hay que hacer modificaciones.

    //Funcion para insertar los usuarios en la BBDD local:
    fun insertUserIntoDatabase(id: Int, name: String, email: String) =
        database.userQueries.insertUser(name, email)

}