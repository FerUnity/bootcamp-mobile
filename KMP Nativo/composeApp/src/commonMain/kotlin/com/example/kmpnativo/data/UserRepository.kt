package com.example.kmpnativo.data

import com.example.kmpnativo.model.User


class UserRepository(driverFactory: DriverFactory) {
    private val db = AppDatabase(driverFactory.createDriver())
    private val dbQuery = db.userQueries

    fun insertUser(username: String, fullname: String, photo: String?){
        dbQuery.insertUser(username, fullname, photo)
    }

    fun getUsers(): List<User> {
//        Obtenemos una lista de objetos de la bbdd y con map hay que convertirlos a una lista de usuarios:
        return dbQuery.selectAllUsers().executeAsList()
           /* .map {
            User(it.id, it.username, it.fullname, it.photo)
        }*/
    }
}