package com.example.mi_kmp_demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform