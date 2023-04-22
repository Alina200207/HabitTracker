package com.example.habits.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.constants.Constants
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType


@Dao
interface HabitsDao {

    @Query("SELECT * FROM ${Constants.databaseName} WHERE habitType = :habitType")
    fun getHabitsByType(habitType: HabitType): LiveData<List<HabitInformation>>

    @Update
    fun updateHabits(vararg habits: HabitInformation)

    @Insert
    fun insertAll(vararg habits: HabitInformation)

    @Delete
    fun delete(habit: HabitInformation)

}