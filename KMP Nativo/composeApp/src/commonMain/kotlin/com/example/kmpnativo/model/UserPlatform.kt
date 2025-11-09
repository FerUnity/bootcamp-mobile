package com.example.kmpnativo.model

//Todas las fun genericas para todas las plataformas:

expect fun getUserName(): String
expect suspend fun getPhoto(): String?
//Se usa suspend para permitir que en un andrioid el user se pueda sacar una foto o que la busque en el dispositivo.
//Y en el desktop que busca una foto en el sistema.

//Una fun suspend se usa para marcarla como una función de suspensión,
// lo que permite que la ejecución se pause y se reanude posteriormente sin bloquear el hilo principal.
// Esto es útil para operaciones de larga duración,
// como solicitudes de red o cálculos pesados,
// haciendo que la interfaz de usuario se mantenga receptiva.
// Las funciones de suspensión solo pueden ser llamadas desde una corrutina o desde otra función de suspensión.