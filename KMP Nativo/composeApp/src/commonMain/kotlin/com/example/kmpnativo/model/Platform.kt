package com.example.kmpnativo.model

//Esta interfaz es un objeto llamado Plattform. Lo unico que tiene es un nombre.
//En cada sourceSet ese nombre sera el de la plataforma correspopndiente al sistena operativo respectivo.
interface Platform {
    val name: String
}

//Creamos 4 fun que deberan implementarse en ambas plataformas aunque no tenga sentido la pregunta.
// La fun getPlattform() retorna un nombre: El de la clase Platform aca arriba:
//Esta fun getPlatform() es llamamda desde el Greeting,
// pero en cada plataforma llamara al getPlatform() del sourceSet:
expect fun getPlatform(): Platform

//Para obtener la ruta del external storage de la mobile app y desktop app
expect fun getUserHomeDir(): String

//Inyterfaz que contiene una fun para obtener el Battery level.
// Esta fun solo retornara un valor en Mobile, en Desaktop ret null:
interface BatteryLevel {
    fun getBatteryLevel(): Int?// Por si no se puede devolver ese valor que se ret nulo, para que se pueda retornar algo:
}

//Fun solo para Desktop. Igual hay que implementar para todas las plataformas.
// Obtener el usuraio conectado:
expect fun getSystemUserName(): String?