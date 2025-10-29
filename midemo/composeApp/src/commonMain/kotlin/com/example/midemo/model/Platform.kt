package com.example.midemo.model

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform