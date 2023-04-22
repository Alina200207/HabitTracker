package com.example.habits.database

import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HabitsRepository(private val habitsDao: HabitsDao) {

    val goodHabits = habitsDao.getHabitsByType(HabitType.Good)
    val badHabits = habitsDao.getHabitsByType(HabitType.Bad)

//    fun getGoodHabits(): LiveData<List<HabitInformation>> {
//        return habitsDao.getHabitsByType(HabitType.Good)
//    }
//
//    fun getBadHabits(): LiveData<List<HabitInformation>> {
//        return habitsDao.getHabitsByType(HabitType.Bad)
//    }

    suspend fun insert(habitInformation: HabitInformation) {
        withContext(Dispatchers.IO) { habitsDao.insertAll(habitInformation) }
    }

    suspend fun update(habitInformation: HabitInformation) {
        withContext(Dispatchers.IO) { habitsDao.updateHabits(habitInformation) }
    }

}