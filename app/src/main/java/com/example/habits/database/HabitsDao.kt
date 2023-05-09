package com.example.habits.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.constants.Constants
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType


@Dao
interface HabitsDao {

    @Query("SELECT * FROM ${Constants.databaseName}")
    fun getHabits(): List<HabitInformation>

    @Query("SELECT * FROM ${Constants.databaseName} WHERE habitType = :habitType")
    fun getHabitsByType(habitType: HabitType): LiveData<List<HabitInformation>>

    @Query("SELECT * FROM ${Constants.databaseName} WHERE id = :habitId")
    fun getHabitById(habitId: Long): LiveData<HabitInformation>

    @Update
    fun updateHabits(vararg habits: HabitInformation)

    @Insert
    fun insert(habit: HabitInformation): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habits: HabitInformation): List<Long>

    @Delete
    fun delete(habit: HabitInformation)

    @Query("DELETE FROM ${Constants.databaseName}")
    fun deleteAll()

}