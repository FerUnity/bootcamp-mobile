package com.example.midemo.viewmodel

import com.example.midemo.model.getPlatform

//Esta clase Greeting (Es un ViewModel) es comun porque cambiara la vista,
// segun sea donde se compila este proyecto
class Greeting {
    private val platform = getPlatform()
//    Por ende, segun donde se construya el proyecto: Android o Desktop,
//    es que se llamara a la fun getPlatform() correspondiente

    fun greet(name: String): String {
        return "Hola $name, desde ${platform.name}!"
    }
}