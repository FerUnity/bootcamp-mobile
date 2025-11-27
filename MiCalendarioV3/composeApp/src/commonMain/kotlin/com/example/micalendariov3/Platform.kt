package com.example.micalendariov3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform