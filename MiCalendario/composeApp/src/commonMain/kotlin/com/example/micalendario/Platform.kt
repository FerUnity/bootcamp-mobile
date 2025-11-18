package com.example.micalendario

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform