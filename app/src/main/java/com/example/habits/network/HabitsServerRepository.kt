package com.example.habits.network

import com.example.habits.database.HabitsDatabaseRepository
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.Uid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HabitsServerRepository(
    private val habitsApiService: HabitsApiService,
    private val habitsDatabaseRepository: HabitsDatabaseRepository
) {
    suspend fun updateHabitsList() {
        withContext(Dispatchers.IO) {
            val notSyncedHabits =
                habitsDatabaseRepository.getAllHabits().filter { habit -> !habit.isSynced }
            if (notSyncedHabits.isNotEmpty()) {
                for (habit in notSyncedHabits) {
                    habitsApiService.putHabit(habit)
                }
            }
            val response = habitsApiService.getHabits()
            if (response.isSuccessful) {
                habitsDatabaseRepository.deleteAllHabits()
                habitsDatabaseRepository.insertAll(*(response.body() ?: arrayOf()))
            }
        }
    }

    suspend fun insertOrUpdateHabit(habit: HabitInformation, id: Long) {
        withContext(Dispatchers.IO) {
            val response = habitsApiService.putHabit(habit)
            if (response.isSuccessful) {
                val uid = response.body()
                if (uid != null) {
                    habit.id = id
                    habit.uid = uid.uid
                    habit.isSynced = true
                }
                habitsDatabaseRepository.update(habit)
            }
        }
    }



    suspend fun deleteHabit(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsApiService.deleteHabit(Uid(habit.uid))
        }
    }

}