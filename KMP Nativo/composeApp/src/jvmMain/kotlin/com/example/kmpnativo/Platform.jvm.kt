package com.example.kmpnativo

import com.example.kmpnativo.model.Platform

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()