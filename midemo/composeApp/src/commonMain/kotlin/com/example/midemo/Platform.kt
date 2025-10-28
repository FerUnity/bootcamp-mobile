package com.example.midemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform