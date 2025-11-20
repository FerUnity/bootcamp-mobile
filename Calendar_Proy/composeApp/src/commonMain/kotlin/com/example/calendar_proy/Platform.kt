package com.example.calendar_proy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform