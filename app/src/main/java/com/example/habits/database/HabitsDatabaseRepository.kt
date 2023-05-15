package com.example.habits.database

import androidx.lifecycle.LiveData
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HabitsDatabaseRepository(private val habitsDao: HabitsDao) {

    val goodHabits = habitsDao.getHabitsByType(HabitType.Good)
    val badHabits = habitsDao.getHabitsByType(HabitType.Bad)

    suspend fun insertAll(vararg habits: HabitInformation): List<Long> {
        return withContext(Dispatchers.IO) {
            habitsDao.insertAll(*habits)
        }
    }

    suspend fun insert(habit: HabitInformation): Long {
        return withContext(Dispatchers.IO) {
            habitsDao.insert(habit)
        }
    }

    suspend fun update(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsDao.updateHabits(habit)
        }
    }

    suspend fun getAllHabits(): List<HabitInformation> {
        return withContext(Dispatchers.IO) {
            habitsDao.getHabits()
        }
    }

    suspend fun deleteAll() {
        return withContext(Dispatchers.IO) {
            habitsDao.deleteAll()
        }
    }

    suspend fun delete(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsDao.delete(habit)
        }
    }

    fun getHabitById(id: Long): LiveData<HabitInformation> {
        return habitsDao.getHabitById(id)
    }

    fun getHabit(id: Long): HabitInformation {
        return habitsDao.getHabit(id)
    }
}