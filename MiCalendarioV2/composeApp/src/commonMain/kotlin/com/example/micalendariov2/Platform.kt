package com.example.micalendariov2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform