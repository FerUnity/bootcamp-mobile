package com.example.persistenciaynetworkingmultiplatform.ktor

import kotlinx.serialization.Serializable

//Esta data class corresponde al modelo.
// Es la data class que define los campos de cada elemento que se puede obtener del servicio remoto,
// o los campos de c/elemento que se puede agregar a la BBDD local:
@Serializable
data class UserApi(
    val id: Int,
    val name: String,
    val email: String
)
