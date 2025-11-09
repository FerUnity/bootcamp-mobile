package com.example.kmpnativo.model

actual fun getUserName(): String {
    return "" //No hau user en Android
}


//La sgte fun es para buscar un archivo de img o sacarse una foto:
actual suspend fun getPhoto(): String? {
    //Para levantar la camara hay que hacer una integracion con Compose Activity
    return null //Por mientras ret null
}