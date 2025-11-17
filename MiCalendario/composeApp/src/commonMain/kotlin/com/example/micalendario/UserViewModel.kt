package com.example.micalendario


class UserViewModel(
    private val userRepository: UserRepository
) {
    suspend fun load() {
        val users = userRepository.getUsersFromApi()
    }
}