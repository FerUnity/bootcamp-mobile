package com.example.persistenciaynetworkingmultiplatform

import com.example.persistenciaynetworkingmultiplatform.ktor.UserApiService
import com.example.persistenciaynetworkingmultiplatform.sqldelight.Database
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

//Entioendfoi que es para administracion automatica de las librerias:
val appModule = module {
    single { UserApiService() }
    single { Database(get()) }
    single { UserRepository(get(), get()) }
}

val winModule = module {}

val androidModule = module {}

fun initKoin() = startKoin {
    modules(appModule)
}