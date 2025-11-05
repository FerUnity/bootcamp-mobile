package com.example.kmpnativo.model

//clase que implementa una fun para saludar:
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}