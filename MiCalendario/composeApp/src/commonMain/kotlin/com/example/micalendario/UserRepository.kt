package com.example.micalendario

import com.example.micalendario.ktor.UserApi
import com.example.micalendario.ktor.UserApiService
import com.example.micalendario.sqldelight.Database

class UserRepository (
    private val api: UserApiService,
    private val database: Database
) {
    suspend fun getUsersFromApi(): List<UserApi> = api.getFeriados() //Obtiene los feriados desde la API
    suspend fun getUsersFromDb(): List<UserApi> = api.getFeriados()
}