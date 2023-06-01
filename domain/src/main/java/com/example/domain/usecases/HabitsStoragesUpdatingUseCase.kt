package com.example.domain.usecases

import android.util.Log
import com.example.domain.entities.HabitInformation
import com.example.domain.entities.ServerSynchronization
import com.example.domain.interfaces.ServerRepositoryInterface
import javax.inject.Inject

class HabitsStoragesUpdatingUseCase @Inject constructor(private val serverRepository: ServerRepositoryInterface) {
    suspend fun saveHabit(habit: HabitInformation) {
        if (habit.id == 0L)
            serverRepository.insertHabit(habit)
        else
            serverRepository.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitInformation) {
        habit.isSynced = ServerSynchronization.NotSynchronizedDeletion
        serverRepository.deleteHabit(habit)
    }
}