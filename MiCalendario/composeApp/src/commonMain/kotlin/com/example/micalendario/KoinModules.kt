package com.example.micalendario

import com.example.micalendario.ktor.UserApiService
import com.example.micalendario.sqldelight.Database
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val appModule = module {
    single { UserApiService() }
    single { Database(get()) }
    single { UserRepository(get(), get()) }
}

val winModule = module {}

val androidModule = module {}

//fun inicial que carga los modulos de Koin:
fun initKoin() = startKoin {
    modules(appModule)
}