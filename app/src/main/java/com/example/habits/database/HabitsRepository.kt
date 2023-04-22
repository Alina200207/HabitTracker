package com.example.habits.database

import androidx.lifecycle.LiveData
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType

class HabitsRepository(private val habitsDao: HabitsDao) {

    val goodHabits = habitsDao.getHabitsByType(HabitType.Good)
    val badHabits = habitsDao.getHabitsByType(HabitType.Bad)


    fun insert(habitInformation: HabitInformation) {
        habitsDao.insertAll(habitInformation)
    }

    fun update(habitInformation: HabitInformation) {
        habitsDao.updateHabits(habitInformation)
    }

}