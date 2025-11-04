package com.example.persistenciaynetworkingmultiplatform.ktor

import io.ktor.client.call.body
import io.ktor.client.request.get


//Creamos la clase que admin los endpoints del servcicio remoto: Api. Como retrofit

class UserApiService {
    //Este Objeto es que va a generar la conexion con el servicio remoto. Es un puente al servicio remoto:
    private val client = HttpClientFactory.client

//Api Dummy para practicar:
    private val baseUrl = "https://jsonplaceholder.typicode.com"

//    Corresponde a la lista de users en la API:
    suspend fun getUsers(): List<UserApi> =
        client.get("$baseUrl/users").body()
}