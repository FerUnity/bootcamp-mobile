package com.example.ppersonalconkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform