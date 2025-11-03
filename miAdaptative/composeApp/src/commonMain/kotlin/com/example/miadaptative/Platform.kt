package com.example.miadaptative

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform