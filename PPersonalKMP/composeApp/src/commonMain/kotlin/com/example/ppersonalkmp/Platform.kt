package com.example.ppersonalkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform