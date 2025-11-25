package com.example.micalendario.network

import io.ktor.client.HttpClient

// Cliente Ktor simple, sin ContentNegotiation ni KotlinxSerializationConverter
val httpClient = HttpClient()