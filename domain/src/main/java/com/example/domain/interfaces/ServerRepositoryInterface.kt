package com.example.domain.interfaces

import com.example.domain.entities.HabitInformation

interface ServerRepositoryInterface {
    suspend fun synchronizeDatabaseAndServer()

    suspend fun updateHabit(habit: HabitInformation)

    suspend fun insertHabit(habit: HabitInformation)

    suspend fun deleteHabit(habit: HabitInformation)

    suspend fun postHabitDone(habit: HabitInformation)
}