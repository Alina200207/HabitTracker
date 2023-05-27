package com.example.domain.interfaces

import com.example.domain.entities.HabitInformation
import kotlinx.coroutines.flow.Flow

interface DatabaseRepositoryInterface {
    suspend fun insertAll(vararg habits: HabitInformation)

    suspend fun insert(habit: HabitInformation): Long

    suspend fun update(habit: HabitInformation)

    suspend fun delete(habit: HabitInformation)

    suspend fun deleteAll()

    suspend fun getHabitById(id: Long): Flow<HabitInformation>
}