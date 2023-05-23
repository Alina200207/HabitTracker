package com.example.habits.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return appContext
    }
}