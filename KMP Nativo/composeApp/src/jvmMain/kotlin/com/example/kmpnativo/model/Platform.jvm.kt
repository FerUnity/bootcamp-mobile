package com.example.kmpnativo.model

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

//Esta fun getPlatform() es llamamda desde el Greeting
actual fun getPlatform(): Platform = JVMPlatform()

//Para obtener la ruta del external storage del desktop app:
actual fun getUserHomeDir(): String = System.getProperty("user.home")

class DesktopBatteryLevel: BatteryLevel{
    override fun getBatteryLevel(): Int? {
        // Desktop platforms do not typically have a battery or a standard way to
        // retrieve its level. Returning null to indicate unavailability.
        return null
    }

}

actual fun getSystemUserName(): String? {
    return System.getProperty("user.name")?: "Desconocido"

}