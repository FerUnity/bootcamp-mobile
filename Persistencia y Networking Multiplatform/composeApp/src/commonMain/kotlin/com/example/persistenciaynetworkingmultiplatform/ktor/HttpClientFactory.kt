package com.example.persistenciaynetworkingmultiplatform.ktor

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

//Este Objeto es que va a generar la conexion con el servicio remoto. Es un puente al servicio remoto:
object HttpClientFactory {
    val client = HttpClient {
        install(ContentNegotiation){
            json(Json {
               ignoreUnknownKeys = true //Para ignorar errores de serialización por cambios del servicio remoto
            })

        }
    }
}