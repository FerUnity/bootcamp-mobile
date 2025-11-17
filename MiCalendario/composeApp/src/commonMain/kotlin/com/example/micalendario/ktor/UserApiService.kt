package com.example.micalendario.ktor

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.request

class UserApiService {
    private val client = HttpClientFactory.client
    private val baseUrl = "https://date.nager.at/api/v3/PublicHolidays/2025/"

//    fun para obtener async la lista de users desde el API:
    suspend fun getFeriados(): List<UserApi> =
        client.get("$baseUrl/CL").body()

//    fun para crear un user en la Api, ver si esta bien:
    suspend fun createUser(): UserApi =
        client.request("$baseUrl/users").body()
}