package com.example.data.database.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.domain.entities.HabitInformation
import com.example.domain.constants.Constants
import com.example.domain.entities.HabitType
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitsDao {

    @Query("SELECT * FROM ${Constants.database}")
    fun getHabits(): List<HabitInformationEntity>

    @Query("SELECT * FROM ${Constants.database} WHERE habitType = :habitType")
    fun getHabitsByType(habitType: HabitType): Flow<List<HabitInformationEntity>>

    @Query("SELECT * FROM ${Constants.database} WHERE id = :habitId")
    fun getHabitById(habitId: Long): Flow<HabitInformationEntity>

    @Query("SELECT * FROM ${Constants.database} WHERE id = :habitId")
    fun getHabit(habitId: Long): HabitInformationEntity

    @Update
    fun updateHabits(vararg habits: HabitInformationEntity)

    @Insert
    fun insert(habit: HabitInformationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habits: HabitInformationEntity): List<Long>

    @Delete
    fun delete(habit: HabitInformationEntity)

    @Query("DELETE FROM ${Constants.database}")
    fun deleteAll()

}