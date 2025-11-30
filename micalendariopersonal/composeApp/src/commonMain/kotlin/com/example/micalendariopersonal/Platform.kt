package com.example.micalendariopersonal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform