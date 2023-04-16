package com.example.habits.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType


@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<HabitInformation>>

    @Query("SELECT * FROM habits WHERE habitType = :habitType")
    fun getHabitsByType(habitType: HabitType): LiveData<List<HabitInformation>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabit(id: Int): HabitInformation

    @Update
    fun updateHabits(vararg habits: HabitInformation)

    @Insert
    fun insertAll(vararg habits: HabitInformation)

    @Delete
    fun delete(habit: HabitInformation)
}