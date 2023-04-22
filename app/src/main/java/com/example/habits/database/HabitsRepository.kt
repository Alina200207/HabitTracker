package com.example.habits.database

import androidx.lifecycle.LiveData
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType

class HabitsRepository(private val habitsDao: HabitsDao) {

    fun getGoodHabits(): LiveData<List<HabitInformation>> {
        return habitsDao.getHabitsByType(HabitType.Good)
    }

    fun getBadHabits(): LiveData<List<HabitInformation>> {
        return habitsDao.getHabitsByType(HabitType.Bad)
    }

    fun insert(habitInformation: HabitInformation) {
        habitsDao.insertAll(habitInformation)
    }

    fun update(habitInformation: HabitInformation) {
        habitsDao.updateHabits(habitInformation)
    }

}