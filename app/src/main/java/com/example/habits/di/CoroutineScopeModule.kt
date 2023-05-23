package com.example.habits.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
class CoroutineScopeModule(private val appScope: CoroutineScope) {

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return appScope
    }
}