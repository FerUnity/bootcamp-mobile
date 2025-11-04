package com.example.persistenciaynetworkingmultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform