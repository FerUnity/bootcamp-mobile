package com.example.kmpnativo.model

//Este data class rep el objeto de la bbdd:
data class User(
    val id: Long,
    val username: String,
    val fullname: String,
    val photo: String?
)
