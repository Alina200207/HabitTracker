package com.example.domain.di

import com.example.domain.interfaces.ServerRepositoryInterface
import com.example.domain.usecases.HabitsSortAndFilterUseCase
import com.example.domain.usecases.HabitsStoragesUpdatingUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideHabitsUseCase(
        serverRepository: ServerRepositoryInterface
    ): HabitsStoragesUpdatingUseCase {
        return HabitsStoragesUpdatingUseCase(serverRepository)
    }

    @Singleton
    @Provides
    fun provideHabitsSortAndFilterUseCase(): HabitsSortAndFilterUseCase {
        return HabitsSortAndFilterUseCase()
    }
}