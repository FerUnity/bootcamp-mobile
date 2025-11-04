package com.example.persistenciaynetworkingmultiplatform

//Intermed entre el modelo y la vista:
class UserViewModel(
//    Ref al repositorio: Para por ej obtener los users del Api remoto o de la BBDD local:
    private val userRepository: UserRepository
) {
//    Cargamos una lista de usuarios registrados en la API remoto:
    suspend fun load() {
        val users = userRepository.getUsersFromApi()
    }
}