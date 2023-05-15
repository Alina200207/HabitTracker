package com.example.habits.network

import com.example.habits.database.HabitsDatabaseRepository
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.ServerSynchronization
import com.example.habits.entities.Uid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class HabitsServerRepository(
    private val habitsApiService: HabitsApiService,
    private val habitsDatabaseRepository: HabitsDatabaseRepository
) {
    suspend fun updateHabitsList() {
        withContext(Dispatchers.IO) {
            val notSyncedChangedHabits =
                habitsDatabaseRepository.getAllHabits()
                    .filter { habit -> habit.isSynced == ServerSynchronization.NotSynchronizedChange }
            val notSyncedDeletedHabits = habitsDatabaseRepository.getAllHabits()
                .filter { habit -> ((habit.isSynced == ServerSynchronization.NotSynchronizedDeletion) and (habit.uid != "0"))}
            val notSyncedDeletedHabitsInDatabase = habitsDatabaseRepository.getAllHabits()
                .filter { habit -> ((habit.isSynced == ServerSynchronization.NotSynchronizedDeletion) and (habit.uid == "0"))}
            if (notSyncedDeletedHabitsInDatabase.isNotEmpty()) {
                for (habit in notSyncedDeletedHabitsInDatabase){
                    habitsDatabaseRepository.delete(habit)
                }
            }
            var lastPutResponse: Response<Uid>? = null
            if (notSyncedChangedHabits.isNotEmpty()) {
                for (i in notSyncedChangedHabits.indices){
                    if (i == notSyncedDeletedHabits.size - 1)
                        lastPutResponse = habitsApiService.putHabit(notSyncedChangedHabits[i])
                    else
                        habitsApiService.putHabit(notSyncedChangedHabits[i])
                }
                for (habit in notSyncedChangedHabits) {
                    habitsApiService.putHabit(habit)
                }
            }
            var lastDeleteResponse: Response<Unit>? = null
            if (notSyncedDeletedHabits.isNotEmpty()){
                for (i in notSyncedDeletedHabits.indices){
                    if (i == notSyncedDeletedHabits.size - 1)
                        lastDeleteResponse = habitsApiService.deleteHabit(Uid(notSyncedDeletedHabits[i].uid))
                    else
                        habitsApiService.deleteHabit(Uid(notSyncedDeletedHabits[i].uid))
                }
            }
            if ((lastDeleteResponse?.isSuccessful == true and (lastPutResponse?.isSuccessful == true)) or
                (lastDeleteResponse?.isSuccessful == true and notSyncedChangedHabits.isEmpty()) or
                (lastPutResponse?.isSuccessful == true and notSyncedDeletedHabits.isEmpty()) or
                (notSyncedDeletedHabits.isEmpty() and notSyncedChangedHabits.isEmpty()))
            {
                val response = habitsApiService.getHabits()
                if (response.isSuccessful) {
                    habitsDatabaseRepository.deleteAll()
                    habitsDatabaseRepository.insertAll(*(response.body() ?: arrayOf()))
                }
            }
        }
    }

    suspend fun updateHabit(habit: HabitInformation){
        withContext(Dispatchers.IO) {
            habitsDatabaseRepository.update(habit)
            val response = habitsApiService.putHabit(habit)
            if (response.isSuccessful) {
                val uid = response.body()
                if (uid != null) {
                    habit.uid = uid.uid
                    habit.isSynced = ServerSynchronization.SynchronizedChange
                }
                habitsDatabaseRepository.update(habit)
            }
        }
    }

    suspend fun insertHabit(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            val id = habitsDatabaseRepository.insert(habit)
            val response = habitsApiService.putHabit(habit)
            if (response.isSuccessful) {
                val uid = response.body()
                if (uid != null) {
                    habit.id = id
                    habit.uid = uid.uid
                    habit.isSynced = ServerSynchronization.SynchronizedChange
                }
                habitsDatabaseRepository.update(habit)
            }
        }
    }


    suspend fun deleteHabit(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsDatabaseRepository.update(habit)
            var notSyncedHabitAfterUpdating: HabitInformation? = null
            if (!delete(habit).isSuccessful)
            {
                notSyncedHabitAfterUpdating = habitsDatabaseRepository.getHabit(habit.id)
            }
            notSyncedHabitAfterUpdating?.let { delete(it) }
        }
    }

    private suspend fun delete(habit: HabitInformation): Response<Unit>{
        val response = habitsApiService.deleteHabit(Uid(habit.uid))
        if (response.isSuccessful) {
            habitsDatabaseRepository.delete(habit)
        }
        return response
    }

}