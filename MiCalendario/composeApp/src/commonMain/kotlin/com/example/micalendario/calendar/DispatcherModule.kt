package com.example.micalendario.calendar

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.gradle.internal.service.Provides

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher